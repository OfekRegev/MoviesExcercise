package com.ofek.moviesexcercise.domain.repositories

import com.ofek.moviesexcercise.domain.objects.MovieObj
import io.reactivex.Completable
import io.reactivex.Single

interface MoviesRepository {
    fun loadMovies(): Completable
    fun getMoviesList(): Single<List<MovieObj>>
    fun saveMovie(movie: MovieObj): Completable
}
