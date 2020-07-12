package com.ofek.moviesexcercise.presentation.favorite_screen

import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.presentation.errors.GenericResponseError
import com.ofek.moviesexcercise.presentation.objects.UiMovie

interface FavoritesView {
    fun onFavoriteMoviesLoaded(favoriteMovies: List<UiMovie>)
    fun onStartLoadingMovies()
    fun onMoviesFailedToLoad()
    fun noFavoriteMoviesFound()
}