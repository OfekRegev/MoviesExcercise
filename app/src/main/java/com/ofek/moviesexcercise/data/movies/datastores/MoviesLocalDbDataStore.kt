package com.ofek.moviesexcercise.data.movies.datastores

import com.ofek.moviesexcercise.domain.objects.MovieObj
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface MoviesLocalDbDataStore {
    fun saveMoviesToLocalDb(movies: List<MovieObj>) : Completable
    fun getFavoriteMoviesList(): Single<List<MovieObj>>
    fun getFavoriteMovie(id: Int): Observable<MovieObj>
    fun removeMovieFromDb(movieObj: MovieObj): Completable

}
