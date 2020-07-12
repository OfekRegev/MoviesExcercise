package com.ofek.moviesexcercise.data.movies.repos

import com.ofek.moviesexcercise.data.movies.datastores.MoviesLocalDbDataStore
import com.ofek.moviesexcercise.data.movies.datastores.MoviesApiDataStore
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.objects.PagingResult
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * this repository handles business logic related to movies data
 */
class MoviesRepoImp(
    private val apiDataStore: MoviesApiDataStore,
    private val localDb: MoviesLocalDbDataStore
) : MoviesRepository {
    /**
     * loading the movies from an api, then saving it into local DB
     */
    override fun getFavoriteMovies(): Single<List<MovieObj>> {
        return localDb.getFavoriteMoviesList()
    }

    /**
     * loads all movies from the local db
     */
    override fun getMoviesList(page: Int): Single<PagingResult<List<MovieObj>>> {
        return apiDataStore.loadMovies(page)
            .flatMap { pagingResult ->
                val moviesList = pagingResult.result
                Observable.fromIterable(moviesList)
                    // it's necessary to find the favorite movies because it won't return from the api
                    // thus, the function searches the movie in the db, if it's exist then the favorite field changes to true
                    // otherwise, the favorite flag will be false
                    .flatMap { movieObj ->
                        localDb.getFavoriteMovie(movieObj.id)
                            .map { it.favorite }
                            // if the movie doesn't exist in the db an exception will be thrown
                            .onErrorReturn { false }
                            .map {
                                movieObj.copy(favorite = it)
                            }
                    }.toList().map { finalMoviesList ->
                        // after mapping the favorite field to the movies, apply the new list to the new paging result
                        // and copy the other data from the original result
                        pagingResult.copy(result = finalMoviesList)
                    }
            }
    }

    override fun addMovieToFavorites(movieObj: MovieObj): Completable {
        // currently in general only favorite movies will be saved in the db
        // but, if for some reason it will change and the movie already exited in the db, the new movie will override the old one.
        return localDb.saveMoviesToLocalDb(listOf(movieObj))
    }

    override fun removeMovieFromFavorites(movieObj: MovieObj): Completable {
        // currently only favorite movies is saved in the db, thus removing from favorites requires removing from the db.
        // if this logic changes for some reason it's required to edit this function
        return localDb.removeMovieFromDb(movieObj)
    }

}