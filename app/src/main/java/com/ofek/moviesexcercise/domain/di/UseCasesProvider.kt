package com.ofek.moviesexcercise.domain.di;

import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.objects.PagingResult
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository
import com.ofek.moviesexcercise.domain.usecases.AddMovieToFavorites
import com.ofek.moviesexcercise.domain.usecases.GetMoviesList
import com.ofek.moviesexcercise.domain.usecases.GetFavoriteMovies
import com.ofek.moviesexcercise.domain.usecases.RemoveMovieFromFavorites
import io.reactivex.CompletableTransformer
import io.reactivex.SingleTransformer


interface UseCasesProvider {
    fun provideGetFavoriteMovies(
        transformer: SingleTransformer<List<MovieObj>,List<MovieObj>>,
        repo: MoviesRepository
    ): GetFavoriteMovies

    fun provideGetMoviesList(
        singleTransformer: SingleTransformer<PagingResult<List<MovieObj>>, PagingResult<List<MovieObj>>>,
        repo: MoviesRepository
    ): GetMoviesList

    fun provideAddMovieToFavorites(
        completableTransformer: CompletableTransformer,
        repo: MoviesRepository
    ): AddMovieToFavorites

    fun provideRemoveMovieToFavorites(
        completableTransformer: CompletableTransformer,
        repo: MoviesRepository
    ): RemoveMovieFromFavorites
}
