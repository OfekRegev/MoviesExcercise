package com.ofek.moviesexcercise.domain.usecases

import com.ofek.moviesexcercise.domain.common.BaseSingleUseCase
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository
import io.reactivex.Single
import io.reactivex.SingleTransformer

/**
 * represents the use case to retrieve the movies list
 */
public class GetMoviesList(
    private val transformer: SingleTransformer<List<MovieObj>
            , List<MovieObj>>, private val moviesRepository: MoviesRepository
) : BaseSingleUseCase<List<MovieObj>>(transformer) {


    override fun createSourceSingle(params: Map<String?, Any?>?): Single<List<MovieObj>> {
        return moviesRepository.getMoviesList()
    }

    fun getMoviesList(): Single<List<MovieObj>> {
        return createSourceSingle(null)
    }
}