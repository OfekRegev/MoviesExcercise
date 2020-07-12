package com.ofek.moviesexcercise.data.movies.datastores

import com.ofek.moviesexcercise.data.objects.tmbd.PopularResponse
import com.ofek.moviesexcercise.data.objects.tmbd.TmdbMovieDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("popular")
    fun getMoviesList(@Query("page")page: Int,@Query("api_key") apiKey: String): Single<PopularResponse>

}
