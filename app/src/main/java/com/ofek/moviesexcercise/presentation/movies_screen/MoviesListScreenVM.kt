package com.ofek.moviesexcercise.presentation.movies_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.common.collect.ImmutableList
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.objects.PagingResult
import com.ofek.moviesexcercise.domain.usecases.GetMoviesList
import com.ofek.moviesexcercise.presentation.Mappers
import com.ofek.moviesexcercise.presentation.errors.FailedToLoadFirstPageError
import com.ofek.moviesexcercise.presentation.errors.FailedToLoadMoreError
import com.ofek.moviesexcercise.presentation.errors.GenericResponseError
import com.ofek.moviesexcercise.presentation.errors.PresentationError
import com.ofek.moviesexcercise.presentation.objects.UiMovie
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import okhttp3.internal.toImmutableList


@Suppress("UNCHECKED_CAST")
class MoviesListScreenVMFactory(
    private val getMoviesList: GetMoviesList,
    private val observingScheduler: Scheduler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesListScreenVM(getMoviesList, observingScheduler) as T
    }

}

class MoviesListScreenVM(
    private val getMoviesList: GetMoviesList,
    // determines on which thread the observation runs on.
    // regularly it would be main thread but in unit test for example test scheduler can be used
    private val observingScheduler: Scheduler
) : ViewModel() {
    var stateLiveData = MutableLiveData<MoviesListState>()
    var errorLiveData = MutableLiveData<PresentationError>()
    private val compositeDisposable = CompositeDisposable()

    init {
        // initialize with default state to prevent state nullability
        stateLiveData.value = MoviesListState(ImmutableList.of(), false,0,null)
    }

    fun loadMoviesList() {
        val currentState = stateLiveData.value!!
        // do nothing when the current page is the last one
        // when max page is null it means the first page didn't loaded yet
        if (currentState.maxPage != null && currentState.currentPage >= currentState.maxPage) {
            return
        }
        getMoviesList.getMoviesList(currentState.currentPage+1)
            .flatMap { pagingResult ->
                // iterating the list and mapping each domain movie object to ui movie object
                Observable.fromIterable(pagingResult.result)
                    .map { Mappers.mapMovieObjToUiMovie(it) }
                    .toList()
                    .map {
                        // the type of the movies list has changed so a new paging result with copy values is required
                        PagingResult(it,pagingResult.page,pagingResult.maxPage)
                    }
            }
            .observeOn(observingScheduler)
            .subscribe(object : SingleObserver<PagingResult<List<UiMovie>>> {
                var disposable: Disposable? = null
                override fun onSubscribe(d: Disposable) {
                    disposable = d;
                    compositeDisposable.add(d)
                    val newState = stateLiveData.value!!.copy(loading = true)
                    stateLiveData.value = newState
                }

                override fun onSuccess(t: PagingResult<List<UiMovie>>) {
                    val currentState = stateLiveData.value!!
                    val moviesList = currentState.moviesList.toMutableList()
                    moviesList.addAll(t.result)
                    val newState = currentState.copy(
                        moviesList = ImmutableList.copyOf(moviesList),
                        loading = false,
                        currentPage = t.page,
                        maxPage = t.maxPage
                    )
                    stateLiveData.value = newState
                    disposable.let {
                        if (!it!!.isDisposed) {
                            it.dispose()
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    val newState = stateLiveData.value!!.copy(loading = false)
                    stateLiveData.value = newState
                    if (currentState.currentPage>0) {
                        errorLiveData.value = FailedToLoadMoreError()
                    } else{
                        errorLiveData.value = FailedToLoadFirstPageError()
                    }
                    disposable.let {
                        if (!it!!.isDisposed) {
                            it.dispose()
                        }
                    }
                }
            })
    }
}