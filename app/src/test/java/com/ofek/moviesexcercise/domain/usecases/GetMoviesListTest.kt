package com.ofek.moviesexcercise.domain.usecases

import com.ofek.moviesexcercise.domain.common.SynchronousTransformers
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.objects.PagingResult
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class GetMoviesListTest {


    @Test
    fun verifyValueIsCorrect() {

        val mockedRepo = Mockito.mock(MoviesRepository::class.java)
        val getMoviesList = GetMoviesList(SynchronousTransformers.getSingleTransformer(),mockedRepo)
        val pageArg = 1
        val result = PagingResult(emptyList<MovieObj>())

        Mockito.`when`(mockedRepo.getMoviesList(pageArg)).thenReturn(Single.just(result))

        getMoviesList.getMoviesList(pageArg).test().assertValue(result)
    }
}