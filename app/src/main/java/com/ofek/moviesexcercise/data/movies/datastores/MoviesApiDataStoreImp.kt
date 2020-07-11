package com.ofek.moviesexcercise.data.movies.datastores

import com.ofek.moviesexcercise.data.movies.Mappers
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.objects.PagingResult
import io.reactivex.Observable
import io.reactivex.Single

/**
 * responsible to load the movies from api and map it to domain objects
 */
class MoviesApiDataStoreImp(private val service: MoviesService) : MoviesApiDataStore{
    override fun loadMovies(): Single<PagingResult<List<MovieObj>>> {
        TODO("")
    }
}