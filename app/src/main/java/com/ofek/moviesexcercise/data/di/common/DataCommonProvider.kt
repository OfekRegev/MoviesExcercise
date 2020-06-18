package com.ofek.moviesexcercise.data.di.common

import android.content.Context
import com.ofek.moviesexcercise.data.movies.datastores.MoviesService
import com.ofek.moviesexcercise.data.movies.datastores.roomdb.MoviesDao
import com.ofek.moviesexcercise.data.movies.datastores.roomdb.MoviesDb
import com.ofek.moviesexcercise.domain.di.UseCasesModule

/**
 * see [UseCasesModule] for explanation about the module classes
 */
interface DataCommonProvider {
    fun provideMoviesService(): MoviesService
    fun provideMoviesDao(moviesDb: MoviesDb): MoviesDao
    fun provideMoviesDb(context: Context): MoviesDb

}