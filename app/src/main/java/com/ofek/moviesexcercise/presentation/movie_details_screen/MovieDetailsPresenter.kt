package com.ofek.moviesexcercise.presentation.movie_details_screen

import com.ofek.moviesexcercise.presentation.objects.UiMovie

interface MovieDetailsPresenter {
    fun addMovieToFavorites(uiMovie: UiMovie)
    fun removeMovieFromFavorites(uiMovie: UiMovie)
    fun attachView(detailsView: MovieDetailsView)
    fun clearResources()
}