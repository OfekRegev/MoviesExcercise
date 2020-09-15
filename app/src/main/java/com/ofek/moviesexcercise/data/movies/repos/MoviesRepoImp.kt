package com.ofek.moviesexcercise.data.movies.repos

import com.ofek.moviesexcercise.data.movies.datastores.MoviesLocalDbDataStore
import com.ofek.moviesexcercise.data.movies.datastores.MoviesApiDataStore
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository
import io.reactivex.Completable
import io.reactivex.Single

/**
 * this repository handles business logic related to movies data
 */
class MoviesRepoImp(private val apiDataStore: MoviesApiDataStore, private val localDb: MoviesLocalDbDataStore) : MoviesRepository {
    /**
     * loading the movies from an api, then saving it into local DB
     */
    override fun loadMovies(): Completable {
        return apiDataStore.loadMovies().flatMapCompletable { localDb.saveMoviesToLocalDb(it)}
    }

    /**
     * loads all movies from the local db
     */
    override fun getMoviesList(): Single<List<MovieObj>> {
        return localDb.getMoviesList()
    }

    /**
     * save a single movie to the database. in case the movie has been already in the db a SQLiteConstraintException thrown.
     */
    override fun saveMovie(movie: MovieObj): Completable {
        return localDb.saveMovieToLocalDb(movie)
    }
}