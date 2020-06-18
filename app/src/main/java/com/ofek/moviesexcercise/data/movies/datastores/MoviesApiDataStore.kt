package com.ofek.moviesexcercise.data.movies.datastores

import com.ofek.moviesexcercise.domain.objects.MovieObj
import io.reactivex.Single

interface MoviesApiDataStore {
    fun loadMovies(): Single<List<MovieObj>>
}
