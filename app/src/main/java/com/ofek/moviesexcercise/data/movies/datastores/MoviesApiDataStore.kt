package com.ofek.moviesexcercise.data.movies.datastores

import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.objects.PagingResult
import io.reactivex.Single

interface MoviesApiDataStore {
    fun loadMovies(page: Int): Single<PagingResult<List<MovieObj>>>
}
