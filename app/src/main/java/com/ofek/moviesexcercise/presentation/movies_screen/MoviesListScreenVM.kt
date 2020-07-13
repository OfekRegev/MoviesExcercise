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
import io.reactivex.Single
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
    private var compositeDisposable = CompositeDisposable()

    init {
        // initialize with default state to prevent state nullability
        stateLiveData.value = MoviesListState(ImmutableList.of(), false, 0, null)
    }

    /**
     * a method to load the firsts page only
     */
    fun loadMoviesList(refresh: Boolean) {
        val currentState = stateLiveData.value!!
        // when the fragment is reattached this method will be called even though the first page already loaded
        // so if the list not explicitly needs to refresh thus the first page should not load again
        if (!refresh && currentState.currentPage>0) {
            return
        }
        loadMoviesStream(1).subscribe(object : SingleObserver<PagingResult<List<UiMovie>>> {
                var disposable: Disposable? = null
                override fun onSubscribe(d: Disposable) {
                    disposable = d;
                    compositeDisposable.add(d)
                    val newState = stateLiveData.value!!.copy(loading = true)
                    stateLiveData.value = newState
                }

                override fun onSuccess(t: PagingResult<List<UiMovie>>) {
                    val newState = stateLiveData.value!!.copy(
                        moviesList = ImmutableList.copyOf(t.result),
                        loading = false,
                        currentPage = t.page,
                        maxPage = t.maxPage
                    )
                    stateLiveData.value = newState
                    disposable?.let {
                        if (!it.isDisposed) {
                            it.dispose()
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    val newState = stateLiveData.value!!.copy(loading = false)
                    stateLiveData.value = newState
                    errorLiveData.value = FailedToLoadFirstPageError()
                    disposable?.let {
                        if (!it.isDisposed) {
                            it.dispose()
                        }
                    }
                }
            })
    }

    private fun loadMoviesStream(page: Int): Single<PagingResult<List<UiMovie>>> {
        return getMoviesList.getMoviesList(page)
            .flatMap { pagingResult ->
                // iterating the list and mapping each domain movie object to ui movie object
                Observable.fromIterable(pagingResult.result)
                    .map { Mappers.mapMovieObjToUiMovie(it) }
                    .toList()
                    .map {
                        // the type of the movies list has changed so a new paging result with copy values is required
                        PagingResult(it, pagingResult.page, pagingResult.maxPage)
                    }
            }
            .observeOn(observingScheduler)
    }

    fun loadNextPage() {
        var currentState = stateLiveData.value!!
        getMoviesList.getMoviesList(currentState.currentPage + 1)
            .flatMap { pagingResult ->
                // iterating the list and mapping each domain movie object to ui movie object
                Observable.fromIterable(pagingResult.result)
                    .map { Mappers.mapMovieObjToUiMovie(it) }
                    .toList()
                    .map {
                        // the type of the movies list has changed so a new paging result with copy values is required
                        PagingResult(it, pagingResult.page, pagingResult.maxPage)
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
                    currentState = stateLiveData.value!!
                    val moviesList = currentState.moviesList.toMutableList()
                    moviesList.addAll(t.result)
                    val newState = currentState.copy(
                        moviesList = ImmutableList.copyOf(moviesList),
                        loading = false,
                        currentPage = t.page,
                        maxPage = t.maxPage
                    )
                    stateLiveData.value = newState
                    disposable?.let {
                        if (!it.isDisposed) {
                            it.dispose()
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    val newState = stateLiveData.value!!.copy(loading = false)
                    errorLiveData.value = FailedToLoadMoreError()
                    stateLiveData.value = newState
                    disposable?.let {
                        if (!it.isDisposed) {
                            it.dispose()
                        }
                    }
                }
            })
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
        super.onCleared()
    }
}