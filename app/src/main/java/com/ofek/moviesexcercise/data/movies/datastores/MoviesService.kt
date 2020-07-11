package com.ofek.moviesexcercise.data.movies.datastores

import com.ofek.moviesexcercise.data.objects.tmbd.TmdbMovieDto
import io.reactivex.Single
import retrofit2.http.GET

interface MoviesService {

    @GET("movies.json")
    fun getMoviesList(): Single<List<TmdbMovieDto>>

}
