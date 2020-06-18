package com.ofek.moviesexcercise.domain.di;

import com.ofek.moviesexcercise.domain.repositories.MoviesRepository
import com.ofek.moviesexcercise.domain.usecases.LoadMovies
import io.reactivex.CompletableTransformer


class UseCasesProviderImp : UseCasesProvider {
    override fun provideLoadMovies(
        transformer: CompletableTransformer,
        repo: MoviesRepository
    ): LoadMovies {
        return LoadMovies(transformer, repo)
    }
}
