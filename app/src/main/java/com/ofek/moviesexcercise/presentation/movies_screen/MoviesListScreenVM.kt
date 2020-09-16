package com.ofek.moviesexcercise.presentation.movies_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.common.collect.ImmutableList
import com.ofek.moviesexcercise.domain.usecases.GetMoviesList
import com.ofek.moviesexcercise.presentation.Mappers
import com.ofek.moviesexcercise.presentation.errors.GenericResponseError
import com.ofek.moviesexcercise.presentation.errors.PresentationError
import com.ofek.moviesexcercise.presentation.objects.UiMovie
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


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
        stateLiveData.value = MoviesListState(ImmutableList.of(), false)
    }

    fun loadMoviesList() {
        getMoviesList.getMoviesList()
            // iterating the list and mapping each domain movie object to ui movie object
            .flatMapObservable { Observable.fromIterable(it) }
            .map { Mappers.mapMovieObjToUiMovie(it) }
            // applies sorting by release year, by comparing o2 to o1 the sort will be from the newest to the oldest.
            // switch the compression to sort from the oldest to the newest
            .sorted { o1, o2 -> o2.releaseYear.compareTo(o1.releaseYear) }
            .toList()
            .observeOn(observingScheduler)
            .subscribe(object : SingleObserver<List<UiMovie>> {
                var disposable: Disposable? = null
                override fun onSubscribe(d: Disposable) {
                    disposable = d;
                    compositeDisposable.add(d)
                    val newState = stateLiveData.value!!.copy(loading = true)
                    stateLiveData.value = newState
                }

                override fun onSuccess(t: List<UiMovie>) {
                    val newState = stateLiveData.value!!.copy(
                        moviesList = ImmutableList.copyOf(t),
                        loading = false
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
                    errorLiveData.value = GenericResponseError()
                    disposable?.let {
                        if (!it.isDisposed) {
                            it.dispose()
                        }
                    }
                }
            })
    }
}