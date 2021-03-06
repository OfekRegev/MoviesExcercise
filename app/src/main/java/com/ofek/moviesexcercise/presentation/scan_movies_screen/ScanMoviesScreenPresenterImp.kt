package com.ofek.moviesexcercise.presentation.scan_movies_screen;

import com.google.gson.Gson
import com.ofek.moviesexcercise.data.movies.Mappers
import com.ofek.moviesexcercise.data.objects.MovieDto
import com.ofek.moviesexcercise.domain.usecases.SaveMovie
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONException
import org.json.JSONObject

class ScanMoviesScreenPresenterImp(
    private val saveMovie: SaveMovie,
    val observingScheduler: Scheduler
) : ScanMoviesScreenPresenter {

    private val compositeDisposable = CompositeDisposable()
    private var view: ScanMoviesScreenView? = null
    /**
     * this function receives the text result from the scanner and handles it
     */
    override fun processScanResult(result: String) {
        view?.onStartDecodingBarcode()
        // first of all verify the result is in json form
        try {
            val json = JSONObject(result)
        } catch (e:JSONException) {
            view?.onBarcodeInvalid()
            return
        }
        // then parsing the json to a movie object
        val movieObject = Gson().fromJson(result,MovieDto::class.java)
        // then verifying the movie has all properties,
        // if the movie is missing property it means either the json doesn't contain a movie data or the movie data is invalid
        if (movieObject.title == null ||
            movieObject.genre == null ||
            movieObject.image == null ||
            movieObject.releaseYear == null ||
            movieObject.rating == null) {
            view?.onBarcodeInvalid()
            return
        }
        val disposable = saveMovie.saveMovie(Mappers.mapMovieDtoToMovieObj(movieObject))
            .observeOn(observingScheduler)
            .subscribe({
                // success
                view?.onMovieAdded()
            },{
                // error
                view?.onMovieAlreadyExist()
            })
        compositeDisposable.add(disposable)
        // after the input has parsed and 100% valid it's time to save it
    }

    override fun clearResources() {
        compositeDisposable.clear()
    }

    override fun attachView(scanMovieScreen: ScanMoviesScreenView) {
        view = scanMovieScreen
    }
}
