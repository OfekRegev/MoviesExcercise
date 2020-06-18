package com.ofek.moviesexcercise.data.objects

import com.google.gson.annotations.SerializedName

data class MovieDto(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("rating")
	val rating: Double? = null,

	@field:SerializedName("genre")
	val genre: List<String?>? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("releaseYear")
	val releaseYear: Int? = null
)