package com.ofek.moviesexcercise.presentation.scan_movies_screen

interface ScanMoviesScreenView {
    fun onStartDecodingBarcode()
    fun onBarcodeInvalid()
    fun onMovieAdded()
    fun onMovieAlreadyExist()
}