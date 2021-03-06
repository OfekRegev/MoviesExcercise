package com.ofek.moviesexcercise.ui.di

import android.app.Application
import com.ofek.moviesexcercise.data.di.common.DataCommonModule
import com.ofek.moviesexcercise.data.di.managers.DataStoreModule
import com.ofek.moviesexcercise.data.di.repositories.RepositoriesModule
import com.ofek.moviesexcercise.domain.common.AsyncTransformers
import com.ofek.moviesexcercise.domain.di.UseCasesModule
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.presentation.movies_screen.MoviesListScreenVM
import com.ofek.moviesexcercise.presentation.movies_screen.MoviesListScreenVMFactory
import com.ofek.moviesexcercise.presentation.scan_movies_screen.ScanMoviesScreenPresenter
import com.ofek.moviesexcercise.presentation.scan_movies_screen.ScanMoviesScreenPresenterImp
import com.ofek.moviesexcercise.presentation.splash_screen.SplashPresenter
import com.ofek.moviesexcercise.presentation.splash_screen.SplashPresenterImp
import io.reactivex.android.schedulers.AndroidSchedulers
import java.lang.IllegalStateException

object GlobalDependencyProvider {
    private var application: Application? = null
    fun initGlobalProvider(application: Application) {
        this.application = application
    }
    fun provideSplashScreenPresenter() : SplashPresenter {
        if (application == null) {
            throw IllegalStateException("GlobalDependencyProvider isn't initialized yet, call initGlobalProvider() before using this function")
        }
        val moviesDb = DataCommonModule.dataCommonProvider().provideMoviesDb(application!!)
        val service = DataCommonModule.dataCommonProvider().provideMoviesService()
        val moviesDao = DataCommonModule.dataCommonProvider().provideMoviesDao(moviesDb)
        val apiDataStore = DataStoreModule.getManagersProvider().provideMoviesApiDataStore(service)
        val localDb = DataStoreModule.getManagersProvider().provideMoviesLocalDbDataStore(moviesDao)
        val repo = RepositoriesModule.getRepositoriesProvider().provideMoviesRepo(apiDataStore,localDb)
        val loadMovies = UseCasesModule.getUseCasesProvider().provideLoadMovies(AsyncTransformers.getCompletableTransformer(), repo)
        val scheduler = AndroidSchedulers.mainThread()
        return SplashPresenterImp(loadMovies,scheduler)
    }

    fun provideMoviesListScreenVMFactory() : MoviesListScreenVMFactory {
        if (application == null) {
            throw IllegalStateException("GlobalDependencyProvider isn't initialized yet, call initGlobalProvider() before using this function")
        }
        val moviesDb = DataCommonModule.dataCommonProvider().provideMoviesDb(application!!)
        val service = DataCommonModule.dataCommonProvider().provideMoviesService()
        val moviesDao = DataCommonModule.dataCommonProvider().provideMoviesDao(moviesDb)
        val apiDataStore = DataStoreModule.getManagersProvider().provideMoviesApiDataStore(service)
        val localDb = DataStoreModule.getManagersProvider().provideMoviesLocalDbDataStore(moviesDao)
        val repo = RepositoriesModule.getRepositoriesProvider().provideMoviesRepo(apiDataStore,localDb)
        val scheduler = AndroidSchedulers.mainThread()
        val getMoviesList = UseCasesModule.getUseCasesProvider().provideGetMoviesList(AsyncTransformers.getSingleTransformer(), repo)
        return MoviesListScreenVMFactory(getMoviesList,scheduler)
    }

    fun provideScanMoviesScreenPresenter(): ScanMoviesScreenPresenter {
        val moviesDb = DataCommonModule.dataCommonProvider().provideMoviesDb(application!!)
        val service = DataCommonModule.dataCommonProvider().provideMoviesService()
        val moviesDao = DataCommonModule.dataCommonProvider().provideMoviesDao(moviesDb)
        val apiDataStore = DataStoreModule.getManagersProvider().provideMoviesApiDataStore(service)
        val localDb = DataStoreModule.getManagersProvider().provideMoviesLocalDbDataStore(moviesDao)
        val repo = RepositoriesModule.getRepositoriesProvider().provideMoviesRepo(apiDataStore,localDb)
        val scheduler = AndroidSchedulers.mainThread()
        val saveMovie = UseCasesModule.getUseCasesProvider().provideSaveMovie(AsyncTransformers.getCompletableTransformer(), repo)
        return ScanMoviesScreenPresenterImp(saveMovie,scheduler)
    }
}