package com.ofek.moviesexcercise.data.movies

import com.ofek.moviesexcercise.data.objects.MovieDto
import com.ofek.moviesexcercise.data.objects.MovieRoom
import com.ofek.moviesexcercise.domain.objects.MovieObj

/**
 * collection of mapping functions of movie objects
 */
object Mappers {

    fun mapMovieDtoToMovieObj(movieDto: MovieDto) : MovieObj {
        val movieObj = MovieObj()
        movieObj.genre = movieDto.genre.orEmpty()
        movieObj.image = movieDto.image.orEmpty()
        movieObj.title = movieDto.title.orEmpty()
        movieObj.rating = movieDto.rating.let { 0.0 }
        movieObj.releaseYear  = movieDto.releaseYear.let { 0 }
        return movieObj
    }

    fun mapMovieObjToMovieRoom(it: MovieObj) : MovieRoom {
        return MovieRoom(it.image,it.rating,it.genre,it.title,it.releaseYear)
    }

    fun mapRoomMovieToMovieObj(movieRoom: MovieRoom ) : MovieObj {
        val movieObj = MovieObj()
        movieObj.genre = movieRoom.genre.orEmpty()
        movieObj.image = movieRoom.image.orEmpty()
        movieObj.title = movieRoom.title.orEmpty()
        movieObj.rating = movieRoom.rating.let { 0.0 }
        movieObj.releaseYear  = movieRoom.releaseYear.let { 0 }
        return movieObj
    }

}