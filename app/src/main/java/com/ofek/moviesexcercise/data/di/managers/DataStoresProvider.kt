package com.ofek.moviesexcercise.data.di.managers;

import com.ofek.moviesexcercise.data.movies.datastores.MoviesApiDataStore
import com.ofek.moviesexcercise.data.movies.datastores.MoviesLocalDbDataStore
import com.ofek.moviesexcercise.data.movies.datastores.MoviesService
import com.ofek.moviesexcercise.data.movies.datastores.roomdb.MoviesDao

public interface DataStoresProvider {
    fun provideMoviesLocalDbDataStore(moviesDao: MoviesDao): MoviesLocalDbDataStore
    fun provideMoviesApiDataStore(service: MoviesService): MoviesApiDataStore


}
