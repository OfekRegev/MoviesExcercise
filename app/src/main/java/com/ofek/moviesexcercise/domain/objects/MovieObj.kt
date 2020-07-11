package com.ofek.moviesexcercise.domain.objects


data class MovieObj(
    var id: Int,
    var title: String = "",
    var image: String = "",
    var rating: Double = 0.0,
    var releaseYear: String = "",
    var overview: String = "",
    var favorite: Boolean = false
)