package com.ofek.moviesexcercise.domain.di;

import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.objects.PagingResult
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository
import com.ofek.moviesexcercise.domain.usecases.GetMoviesList
import com.ofek.moviesexcercise.domain.usecases.GetFavoriteMovies
import io.reactivex.CompletableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer


class UseCasesProviderImp : UseCasesProvider {
    override fun provideLoadMovies(
        transformer: SingleTransformer<List<MovieObj>,List<MovieObj>>,
        repo: MoviesRepository
    ): GetFavoriteMovies {
        return GetFavoriteMovies(transformer, repo)
    }

    override fun provideGetMoviesList(
        singleTransformer: SingleTransformer<PagingResult<List<MovieObj>>,PagingResult<List<MovieObj>>>,
        repo: MoviesRepository
    ): GetMoviesList {
        return GetMoviesList(singleTransformer,repo)
    }
}
