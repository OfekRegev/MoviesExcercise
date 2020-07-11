package com.ofek.moviesexcercise.presentation.favorite_screen

import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.presentation.errors.GenericResponseError

interface FavoritesView {
    fun onFavoriteMoviesLoaded(favoriteMovies: List<MovieObj>)
    fun onStartLoadingMovies()
    fun onMoviesFailedToLoad()
    fun noFavoriteMoviesFound()
}