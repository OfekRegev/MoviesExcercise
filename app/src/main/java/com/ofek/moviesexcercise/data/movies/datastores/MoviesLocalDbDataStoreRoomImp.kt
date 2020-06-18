package com.ofek.moviesexcercise.data.movies.datastores

import com.ofek.moviesexcercise.data.movies.Mappers
import com.ofek.moviesexcercise.data.movies.datastores.roomdb.MoviesDao
import com.ofek.moviesexcercise.domain.objects.MovieObj
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * responsible to load the data from the dao layer and mapping it to domain object
 */
class MoviesLocalDbDataStoreRoomImp(private val moviesDao: MoviesDao) : MoviesLocalDbDataStore {
    override fun saveMoviesToLocalDb(movies: List<MovieObj>): Completable {
        return Observable.fromIterable(movies)
            // iterating the list and mapping each domain movie object to room movie object
            .map { Mappers.mapMovieObjToMovieRoom(it) }
            .toList()
            .flatMapCompletable { moviesDao.insertMovies(it) }
    }

    override fun getMoviesList(): Single<List<MovieObj>> {
        return moviesDao.getMoviesList()
            // iterating the list and mapping each movie object to domain object
            .flatMapObservable { Observable.fromIterable(it) }
            .map { Mappers.mapRoomMovieToMovieObj(it) }
            .toList()
    }

}