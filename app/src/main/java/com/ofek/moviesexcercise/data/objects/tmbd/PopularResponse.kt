package com.ofek.moviesexcercise.data.objects.tmbd

import com.google.gson.annotations.SerializedName

data class PopularResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_results")
    val totalResult: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("results")
    val moviesList: List<TmdbMovieDto>
)