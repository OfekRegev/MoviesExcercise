package com.ofek.moviesexcercise.presentation.movie_details_screen

interface MovieDetailsView {
    fun onFailedToAddMovieToFavorites()
    abstract fun onFailedToRemoveMovieToFavorites()


}