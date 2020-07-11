package com.ofek.moviesexcercise.presentation.favorite_screen

interface FavoritesPresenter {
    fun loadMovies()
    fun clearPresenter()
    fun attachView(view: FavoritesView)
}