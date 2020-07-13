package com.ofek.moviesexcercise.domain.usecases

import com.ofek.moviesexcercise.domain.common.BaseCompletableUseCase
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository
import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import java.util.HashMap

class RemoveMovieFromFavorites(transformer: CompletableTransformer, private val moviesRepo: MoviesRepository) :
    BaseCompletableUseCase(transformer) {

    companion object{
        const val MOVIE_KEY = "movie"
    }
    override fun createSourceCompletable(params: Map<String, Any>?): Completable {
        params?.let {
            return moviesRepo.removeMovieFromFavorites(it[MOVIE_KEY] as MovieObj)
        } ?: kotlin.run {
            // if there's a problem with the parameter map the exception will crash the application
            // while testing and prevent potential production bug
            throw IllegalStateException()
        }
    }


    public fun removeFromFavorites(movie: MovieObj): Completable {
        val map =  HashMap<String, Any>(1)
        map[MOVIE_KEY] = movie
        return createStream(map)
    }
}