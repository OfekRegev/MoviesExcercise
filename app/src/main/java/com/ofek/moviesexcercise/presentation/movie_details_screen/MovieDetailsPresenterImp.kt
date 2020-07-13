package com.ofek.moviesexcercise.presentation.movie_details_screen

import com.ofek.moviesexcercise.domain.usecases.AddMovieToFavorites
import com.ofek.moviesexcercise.domain.usecases.RemoveMovieFromFavorites
import com.ofek.moviesexcercise.presentation.Mappers
import com.ofek.moviesexcercise.presentation.objects.UiMovie
import io.reactivex.CompletableObserver
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MovieDetailsPresenterImp(
    private val addMovieToFavorites: AddMovieToFavorites
    , private val removeMovieFromFavorites: RemoveMovieFromFavorites,
    // the scheduler which decides on which thread the callback runs on
    private val observingScheduler: Scheduler
) : MovieDetailsPresenter {
    private val compositeDisposable = CompositeDisposable()
    private var detailsView : MovieDetailsView?  = null
    override fun addMovieToFavorites(uiMovie: UiMovie) {
        uiMovie.favorite = true
        addMovieToFavorites.addToFavorites(Mappers.mapUiMovieToMovieObj(uiMovie))
            .observeOn(observingScheduler)
            .subscribe(object : CompletableObserver {
                var disposable: Disposable? = null
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    compositeDisposable.add(d)
                }

                override fun onComplete() {
                    disposable?.let {
                        if (!it.isDisposed) {
                            it.dispose()
                        }
                    }
                }


                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    // if the operation failed for some reason the view should update it's ui
                    // to let the user know the movie didn't added to the favorites
                    detailsView?.onFailedToAddMovieToFavorites()
                    disposable?.let {
                        if (!it.isDisposed) {
                            it.dispose()
                        }
                    }
                }

            })
    }

    override fun removeMovieFromFavorites(uiMovie: UiMovie) {
        removeMovieFromFavorites.removeFromFavorites(Mappers.mapUiMovieToMovieObj(uiMovie))
            .observeOn(observingScheduler)
            .subscribe(object : CompletableObserver {
                var disposable: Disposable? = null
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    compositeDisposable.add(d)
                }

                override fun onComplete() {
                    disposable?.let {
                        if (!it.isDisposed) {
                            it.dispose()
                        }
                    }
                }


                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    // if the operation failed for some reason the view should update it's ui
                    // to let the user know the movie didn't added to the favorites
                    detailsView?.onFailedToRemoveMovieToFavorites()
                    disposable?.let {
                        if (!it.isDisposed) {
                            it.dispose()
                        }
                    }
                }

            })
    }

    override fun attachView(detailsView: MovieDetailsView) {
        this.detailsView = detailsView
    }

    override fun clearResources() {
        compositeDisposable.clear()
    }
}