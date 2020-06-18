package com.ofek.moviesexcercise.presentation.movies_screen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.collect.ImmutableList
import com.ofek.moviesexcercise.domain.common.SynchronousTransformers
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository
import com.ofek.moviesexcercise.domain.usecases.GetMoviesList
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

/**
 * As it's only an example project, I've implemented only a few test just to demonstration.
 * on a real project would be much more tests
 */
@RunWith(JUnit4::class)
class MoviesListScreenVMTest {

    @Rule @JvmField
    public var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun loadMoviesList_verifyExpectedStatesOnLoadSuccess() {
        val dummyList : List<MovieObj> = ArrayList()
        val repo = Mockito.mock(MoviesRepository::class.java)
        val getMovies = GetMoviesList(SynchronousTransformers.getSingleTransformer(),repo)
        val scheduler = TestScheduler()
        val viewModelUnderTest = MoviesListScreenVM(getMovies,scheduler)

        val stateValues = ArrayList<MoviesListState>()
        Mockito.`when`(repo.getMoviesList()).thenReturn(Single.just(dummyList))
        viewModelUnderTest.stateLiveData.observeForever {
            stateValues.add(it)
        }
        viewModelUnderTest.loadMoviesList()

        scheduler.triggerActions()
        Assert.assertEquals(3,stateValues.size)
        Assert.assertEquals(MoviesListState(ImmutableList.of(),false),stateValues[0])
        Assert.assertEquals(MoviesListState(ImmutableList.of(),true),stateValues[1])
        Assert.assertEquals(MoviesListState(ImmutableList.of(),false),stateValues[2])
    }
}