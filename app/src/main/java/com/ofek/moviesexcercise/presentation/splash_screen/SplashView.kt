package com.ofek.moviesexcercise.presentation.splash_screen

import com.ofek.moviesexcercise.presentation.errors.GenericResponseError

interface SplashView {
    fun onMoviesLoaded()
    fun onStartLoadingMovies()
    fun onMoviesFailedToLoad(genericResponseError: GenericResponseError)
}