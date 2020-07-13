package com.ofek.moviesexcercise.data.movies.datastores

import com.ofek.moviesexcercise.data.common.Constants
import com.ofek.moviesexcercise.data.objects.tmbd.PopularResponse
import com.ofek.moviesexcercise.data.objects.tmbd.TmdbMovieDto
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.objects.PagingResult
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito

class MoviesApiDataStoreImpTest {

    @Test
    fun verifyResponseDataMappedCorrectly (){
        val mockedService = Mockito.mock(MoviesService::class.java)
        val moviesApiDataStore = MoviesApiDataStoreImp(mockedService)
        val dummyList = emptyList<TmdbMovieDto>()
        val dummyResponse = PopularResponse(100,250,300,dummyList)
        val dummyPage = 100
        val expectedResult = PagingResult(emptyList<MovieObj>(),100,300)
        Mockito.`when`(mockedService.getMoviesList(dummyPage,Constants.TMDB_API_KEY)).thenReturn(Single.just(dummyResponse))

        moviesApiDataStore.loadMovies(dummyPage).test().assertValue(expectedResult)

    }
}