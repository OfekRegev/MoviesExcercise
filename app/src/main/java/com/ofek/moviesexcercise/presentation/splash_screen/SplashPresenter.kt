package com.ofek.moviesexcercise.presentation.splash_screen

interface SplashPresenter {
    fun loadMovies()
    fun clearPresenter()
    fun attachView(view: SplashView)
}