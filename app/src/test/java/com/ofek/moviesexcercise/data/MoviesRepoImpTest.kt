package com.ofek.moviesexcercise.data

import com.ofek.moviesexcercise.data.movies.datastores.MoviesApiDataStore
import com.ofek.moviesexcercise.data.movies.datastores.MoviesLocalDbDataStore
import com.ofek.moviesexcercise.data.movies.repos.MoviesRepoImp
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.objects.PagingResult
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

/**
 * As it's only an example project, I've implemented only a few test just to demonstration.
 * on a real project would be much more tests
 */
@RunWith(JUnit4::class)
class MoviesRepoImpTest {

    @Test
    fun loadMovies_verifyLocalDbSaveIsCalledWhenMoviesSuccessfullyFetched(){
        val apiDataStore = Mockito.mock(MoviesApiDataStore::class.java)
        val localDb = Mockito.mock(MoviesLocalDbDataStore::class.java)
        val moviesRepository = MoviesRepoImp(apiDataStore,localDb)
        val dummyList = listOf<MovieObj>()
        Mockito.`when`(apiDataStore.loadMovies(1)).thenReturn(Single.just(PagingResult(dummyList)))

        moviesRepository.getMoviesList(1).subscribe()
        Mockito.verify(apiDataStore,Mockito.times(1)).loadMovies(1)
    }
}