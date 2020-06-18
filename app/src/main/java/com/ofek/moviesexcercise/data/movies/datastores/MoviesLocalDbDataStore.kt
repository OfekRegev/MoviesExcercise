package com.ofek.moviesexcercise.data.movies.datastores

import com.ofek.moviesexcercise.domain.objects.MovieObj
import io.reactivex.Completable
import io.reactivex.Single

interface MoviesLocalDbDataStore {
    fun saveMoviesToLocalDb(it: List<MovieObj>) : Completable
    fun getMoviesList(): Single<List<MovieObj>>

}
