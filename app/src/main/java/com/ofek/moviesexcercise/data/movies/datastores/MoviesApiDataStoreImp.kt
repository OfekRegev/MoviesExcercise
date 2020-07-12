package com.ofek.moviesexcercise.data.movies.datastores

import com.ofek.moviesexcercise.data.common.Constants
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.objects.PagingResult
import com.ofek.moviesexcercise.presentation.Mappers
import io.reactivex.Observable
import io.reactivex.Single

/**
 * responsible to load the movies from api and map it to domain objects
 */
class MoviesApiDataStoreImp(private val service: MoviesService) : MoviesApiDataStore {
    override fun loadMovies(page: Int): Single<PagingResult<List<MovieObj>>> {
        return service.getMoviesList(page, Constants.TMDB_API_KEY)
            .map { popularResponse ->
                PagingResult<List<MovieObj>>(
                    // iterate through the list and map all movie objects
                    Observable.fromIterable(popularResponse.moviesList)
                        .map { Mappers.mapMovieDtoToMovieObj(it) }.toList().blockingGet(),
                    popularResponse.page,
                    popularResponse.totalPages
                    )
            }
    }
}