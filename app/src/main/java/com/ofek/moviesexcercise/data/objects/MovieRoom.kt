package com.ofek.moviesexcercise.data.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "moviesTable")
data class MovieRoom(
    @ColumnInfo(name = "image")
    val image: String? = null,
    @ColumnInfo(name = "rating")
    val rating: Double? = null,
    @ColumnInfo(name = "genre")
    val genre: List<String?>? = null,
    @ColumnInfo(name = "title")
    val title: String? = null,
    @ColumnInfo(name = "releaseYear")
    val releaseYear: Int? = null,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString()
)
