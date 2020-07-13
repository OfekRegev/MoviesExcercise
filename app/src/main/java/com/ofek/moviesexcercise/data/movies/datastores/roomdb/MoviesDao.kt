package com.ofek.moviesexcercise.data.movies.datastores.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ofek.moviesexcercise.data.objects.MovieRoom
import com.ofek.moviesexcercise.domain.objects.MovieObj
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(it: List<MovieRoom>) : Completable
    @Query("SELECT * FROM moviesTable")
    fun getMoviesList(): Single<List<MovieRoom>>
    @Query("SELECT * FROM moviesTable WHERE id = :id")
    fun getFavoriteMovie(id: Int): Single<MovieObj>
    @Query("DELETE FROM moviesTable WHERE id = :id")
    fun removeMovie(id: Int): Completable


}