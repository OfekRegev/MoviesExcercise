package com.ofek.moviesexcercise.domain.repositories

import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.objects.PagingResult
import io.reactivex.Completable
import io.reactivex.Single

interface MoviesRepository {
    fun getFavoriteMovies(): Single<List<MovieObj>>
    fun getMoviesList(page: Int): Single<PagingResult<List<MovieObj>>>
    fun addMovieToFavorites(movieObj: MovieObj): Completable
    fun removeMovieFromFavorites(movieObj: MovieObj): Completable
}
