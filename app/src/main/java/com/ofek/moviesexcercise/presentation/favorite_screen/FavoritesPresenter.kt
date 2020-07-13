package com.ofek.moviesexcercise.presentation.favorite_screen

interface FavoritesPresenter {
    fun loadMovies()
    fun clearResources()
    fun attachView(view: FavoritesView)
}