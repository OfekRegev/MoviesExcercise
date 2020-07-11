package com.ofek.moviesexcercise.presentation.favorite_screen

import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.usecases.GetFavoriteMovies
import io.reactivex.Scheduler
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class FavoritesPresentersImp(private val getFavoriteMovies: GetFavoriteMovies,
                         // the scheduler which decides on which thread the callback runs on
                             private val observingScheduler:Scheduler) :
    FavoritesPresenter {
    var favoritesView: FavoritesView? = null
    private val compositeDisposable = CompositeDisposable()
    override fun loadMovies() {
        getFavoriteMovies.getFavorites()
            .observeOn(observingScheduler)
            .subscribe(object:  SingleObserver<List<MovieObj>>{
                var disposable : Disposable? = null
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    compositeDisposable.add(d)
                    favoritesView.let {
                        it!!.onStartLoadingMovies()
                    }
                }

                override fun onSuccess(favoriteMovies: List<MovieObj>) {
                    favoritesView?.let { splashView ->
                        favoriteMovies.isEmpty().let {
                            splashView.onFavoriteMoviesLoaded(favoriteMovies)
                        } ?: kotlin.run {
                            splashView.noFavoriteMoviesFound()
                        }
                    }
                    disposable?.let {
                        if (!it.isDisposed) {
                            it.dispose()
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    favoritesView?.let {
                        it.onMoviesFailedToLoad()
                    }
                    disposable?.let {
                        if (!it.isDisposed) {
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

    /**
     * this method attaches the view to the presenter
     */
    override fun attachView(view: FavoritesView) {
        this.favoritesView = view
    }


}