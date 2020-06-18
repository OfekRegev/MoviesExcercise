package com.ofek.moviesexcercise.data.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "moviesTable")
data class MovieRoom(
    @ColumnInfo(name = "image")
    val image: String = "",
    @ColumnInfo(name = "rating")
    val rating: Double = 0.0,
    @ColumnInfo(name = "genre")
    val genre: String = "",
    // couldn't find any other value to be the primary key.
    @PrimaryKey
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "releaseYear")
    val releaseYear: Int = 0
)
