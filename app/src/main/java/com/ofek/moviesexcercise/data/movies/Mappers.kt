package com.ofek.moviesexcercise.data.movies

import com.ofek.moviesexcercise.data.objects.MovieRoom
import com.ofek.moviesexcercise.data.objects.tmbd.TmdbMovieDto
import com.ofek.moviesexcercise.domain.objects.MovieObj

/**
 * collection of mapping functions of movie objects
 */
object Mappers {

    fun mapMovieDtoToMovieObj(movieDto: TmdbMovieDto): MovieObj {
        return MovieObj(movieDto.id.or(0)
            ,movieDto.originalTitle.orEmpty()
            ,movieDto.posterPath.orEmpty()
            ,movieDto.voteAverage?.let { it } ?: kotlin.run { 0.0 }
            ,movieDto.releaseDate.orEmpty()
            ,movieDto.overview.orEmpty()
            ,false)
    }

    fun mapMovieObjToMovieRoom(movieObj: MovieObj) : MovieRoom {
        return MovieRoom(movieObj.id,movieObj.image,movieObj.rating,movieObj.title,movieObj.releaseYear,movieObj.overview)
    }

    fun mapRoomMovieToMovieObj(movieRoom: MovieRoom ) : MovieObj {
        val movieObj = MovieObj(movieRoom.id)
        movieObj.image = movieRoom.image
        movieObj.title = movieRoom.title
        movieObj.rating = movieRoom.rating
        movieObj.releaseYear  = movieRoom.releaseYear
        movieObj.favorite = movieRoom.favorite
        return movieObj
    }

}