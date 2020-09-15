package com.ofek.moviesexcercise.data.movies.datastores.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ofek.moviesexcercise.data.objects.MovieRoom
import com.ofek.moviesexcercise.domain.objects.MovieObj
import io.reactivex.Completable
import io.reactivex.Single
@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(it: List<MovieRoom>) : Completable
    @Query("SELECT * FROM moviesTable")
    fun getMoviesList(): Single<List<MovieRoom>>
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertMovie(movie: MovieObj): Completable


}