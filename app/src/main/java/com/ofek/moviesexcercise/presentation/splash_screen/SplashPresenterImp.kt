package com.ofek.moviesexcercise.presentation.splash_screen

import com.ofek.moviesexcercise.domain.usecases.LoadMovies
import com.ofek.moviesexcercise.presentation.errors.GenericResponseError
import io.reactivex.CompletableObserver
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class SplashPresenterImp(private val loadMovies: LoadMovies,
                         // the scheduler which decides on which thread the callback runs on
                         private val observingScheduler:Scheduler) : SplashPresenter{
    var splashView: SplashView? = null
    private val compositeDisposable = CompositeDisposable()
    override fun loadMovies() {
        loadMovies.loadMoviesList()
            .observeOn(observingScheduler)
            .subscribe(object:  CompletableObserver{
                var disposable : Disposable? = null
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    compositeDisposable.add(d)
                    splashView.let {
                        it!!.onStartLoadingMovies()
                    }
                }
                override fun onComplete() {
                    splashView.let {
                        it!!.onMoviesLoaded()
                    }
                    disposable.let {
                        if (!it!!.isDisposed) {
                            it.dispose()
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    splashView.let {
                        it!!.onMoviesFailedToLoad(GenericResponseError())
                    }
                    disposable.let {
                        if (!it!!.isDisposed) {
                            it.dispose()
                        }
                    }
                }
            })
    }

    /**
     * callback for clearing objects before this presenter goes out of use
     */
    override fun clearPresenter() {
        compositeDisposable.dispose()
    }


}