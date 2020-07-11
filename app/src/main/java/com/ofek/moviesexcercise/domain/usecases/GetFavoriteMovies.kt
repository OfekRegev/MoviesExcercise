package com.ofek.moviesexcercise.domain.usecases

import com.ofek.moviesexcercise.domain.common.BaseSingleUseCase
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository
import io.reactivex.Single
import io.reactivex.SingleTransformer

class GetFavoriteMovies(private val transformer: SingleTransformer<List<MovieObj>,List<MovieObj>>, val moviesRepo: MoviesRepository) :
    BaseSingleUseCase<List<MovieObj>>(transformer) {
    override fun createSourceSingle(params: Map<String, Any>?): Single<List<MovieObj>> {
        return moviesRepo.getFavoriteMovies()
    }

    public fun getFavorites(): Single<List<MovieObj>> {
        return createStream(null)
    }
}