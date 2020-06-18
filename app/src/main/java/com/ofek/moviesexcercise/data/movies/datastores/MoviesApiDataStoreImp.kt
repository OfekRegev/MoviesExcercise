package com.ofek.moviesexcercise.data.movies.datastores

import com.ofek.moviesexcercise.data.movies.Mappers
import com.ofek.moviesexcercise.domain.objects.MovieObj
import io.reactivex.Observable
import io.reactivex.Single

/**
 * responsible to load the movies from api and map it to domain objects
 */
class MoviesApiDataStoreImp(private val service: MoviesService) : MoviesApiDataStore{
    override fun loadMovies(): Single<List<MovieObj>> {
        return service.getMoviesList()
            // iterating the list and mapping each dto movie object to domain object
            .flatMapObservable { Observable.fromIterable(it)}
            .map { Mappers.mapMovieDtoToMovieObj(it)}.toList()
    }
}