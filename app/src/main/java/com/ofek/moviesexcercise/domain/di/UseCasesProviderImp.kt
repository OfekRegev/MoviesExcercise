package com.ofek.moviesexcercise.domain.di;

import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository
import com.ofek.moviesexcercise.domain.usecases.GetMoviesList
import com.ofek.moviesexcercise.domain.usecases.LoadMovies
import com.ofek.moviesexcercise.domain.usecases.SaveMovie
import io.reactivex.CompletableTransformer
import io.reactivex.SingleTransformer


class UseCasesProviderImp : UseCasesProvider {
    override fun provideLoadMovies(
        transformer: CompletableTransformer,
        repo: MoviesRepository
    ): LoadMovies {
        return LoadMovies(transformer, repo)
    }

    override fun provideGetMoviesList(
        singleTransformer: SingleTransformer<List<MovieObj>, List<MovieObj>>,
        repo: MoviesRepository
    ): GetMoviesList {
        return GetMoviesList(singleTransformer,repo)
    }

    override fun provideSaveMovie(
        completableTransformer: CompletableTransformer,
        repo: MoviesRepository
    ): SaveMovie {
        return SaveMovie(repo,completableTransformer)
    }
}
