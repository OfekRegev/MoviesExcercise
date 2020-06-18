package com.ofek.moviesexcercise.domain.di;

import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository
import com.ofek.moviesexcercise.domain.usecases.GetMoviesList
import com.ofek.moviesexcercise.domain.usecases.LoadMovies
import io.reactivex.CompletableTransformer
import io.reactivex.SingleTransformer


interface UseCasesProvider {
    fun provideLoadMovies(
        transformer: CompletableTransformer,
        repo: MoviesRepository
    ): LoadMovies

    fun provideGetMoviesList(
        singleTransformer: SingleTransformer<List<MovieObj>, List<MovieObj>>,
        repo: MoviesRepository
    ): GetMoviesList
}
