package com.ofek.moviesexcercise.domain.usecases

import com.ofek.moviesexcercise.domain.common.BaseCompletableUseCase
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository
import io.reactivex.Completable
import io.reactivex.CompletableTransformer

class SaveMovie(
    private val repository: MoviesRepository, transformer: CompletableTransformer
) : BaseCompletableUseCase(transformer) {


    companion object {
        private const val MOVIE_KEY = "movie"
    }

    override fun createSourceCompletable(params: Map<String?, Any?>?): Completable {
        val movie = params?.get(MOVIE_KEY) as MovieObj
        return repository.saveMovie(movie)
    }

    fun saveMovie(movieObj: MovieObj) : Completable {
        val params = HashMap<String?,Any>(1)
        params[MOVIE_KEY] = movieObj
        return createStream(params)
    }
}