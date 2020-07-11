package com.ofek.moviesexcercise.data.movies.datastores.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ofek.moviesexcercise.data.objects.MovieRoom

@Database(entities = [MovieRoom::class],version = 3)
abstract class MoviesDb : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}