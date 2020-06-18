package com.ofek.moviesexcercise.domain.usecases

import com.ofek.moviesexcercise.domain.common.BaseCompletableUseCase
import com.ofek.moviesexcercise.domain.common.BaseSingleUseCase
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository
import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer

class LoadMovies(private val transformer: CompletableTransformer, val moviesRepo: MoviesRepository) :
    BaseCompletableUseCase(transformer) {
    override fun createSourceCompletable(params: Map<String?, Any?>?): Completable {
        return moviesRepo.loadMovies()
    }


    public fun loadMoviesList(): Completable {
        return createStream(null)
    }
}