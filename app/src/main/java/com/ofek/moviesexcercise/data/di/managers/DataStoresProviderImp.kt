package com.ofek.moviesexcercise.data.di.managers;

import com.ofek.moviesexcercise.data.movies.datastores.*
import com.ofek.moviesexcercise.data.movies.datastores.roomdb.MoviesDao

public class DataStoresProviderImp : DataStoresProvider {
    override fun provideMoviesLocalDbDataStore(moviesDao: MoviesDao): MoviesLocalDbDataStore {
        return MoviesLocalDbDataStoreRoomImp(moviesDao)
    }

    override fun provideMoviesApiDataStore(service: MoviesService): MoviesApiDataStore {
        return MoviesApiDataStoreImp(service)
    }


}
