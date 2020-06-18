package com.ofek.moviesexcercise.data.di.repositories;

import com.ofek.moviesexcercise.data.movies.datastores.MoviesApiDataStore
import com.ofek.moviesexcercise.data.movies.datastores.MoviesLocalDbDataStore
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository

public interface RepositoriesProvider {
    fun provideMoviesRepo(
        moviesApiDataStore: MoviesApiDataStore,
        localDb: MoviesLocalDbDataStore
    ): MoviesRepository

}
