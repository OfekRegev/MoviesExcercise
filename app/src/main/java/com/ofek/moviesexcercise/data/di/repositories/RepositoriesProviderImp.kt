package com.ofek.moviesexcercise.data.di.repositories;

import com.ofek.moviesexcercise.data.movies.datastores.MoviesApiDataStore
import com.ofek.moviesexcercise.data.movies.datastores.MoviesLocalDbDataStore
import com.ofek.moviesexcercise.data.movies.repos.MoviesRepoImp
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository

class RepositoriesProviderImp : RepositoriesProvider {
    override fun provideMoviesRepo(
        moviesApiDataStore: MoviesApiDataStore,
        localDb: MoviesLocalDbDataStore
    ): MoviesRepository {
        return MoviesRepoImp(moviesApiDataStore,localDb)
    }

}
