package com.ofek.moviesexcercise.domain.usecases

import com.ofek.moviesexcercise.domain.common.BaseSingleUseCase
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.objects.PagingResult
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository
import io.reactivex.Single
import io.reactivex.SingleTransformer

/**
 * represents the use case to retrieve the movies list
 */
class GetMoviesList(
    private val transformer: SingleTransformer<PagingResult<List<MovieObj>>,PagingResult<List<MovieObj>>>, private val moviesRepository: MoviesRepository
) : BaseSingleUseCase<PagingResult<List<MovieObj>>>(transformer) {
    companion object {
        const val PAGE_KEY = "page"
    }

    override fun createSourceSingle(params: Map<String, Any>?): Single<PagingResult<List<MovieObj>>> {
        params?.let {
            return moviesRepository.getMoviesList(params[PAGE_KEY] as Int)
        } ?: kotlin.run {
            // if there's a problem with the parameter map the exception will crash the application
            // while testing and prevent potential production bug
            throw IllegalStateException()
        }
    }
    /**
     * @param page which page of the movies list to load
     */
    fun getMoviesList(page: Int): Single<PagingResult<List<MovieObj>>> {
        val params = HashMap<String,Any>(1)
        params[PAGE_KEY] = page
        return createStream(null)
    }
}