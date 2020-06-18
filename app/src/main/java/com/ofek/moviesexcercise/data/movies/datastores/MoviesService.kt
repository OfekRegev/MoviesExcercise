package com.ofek.moviesexcercise.data.movies.datastores

import com.ofek.moviesexcercise.data.objects.MovieDto
import com.ofek.moviesexcercise.domain.objects.MovieObj
import io.reactivex.Single
import retrofit2.http.GET

interface MoviesService {

    @GET("movies.json")
    fun getMoviesList(): Single<List<MovieDto>>

}
