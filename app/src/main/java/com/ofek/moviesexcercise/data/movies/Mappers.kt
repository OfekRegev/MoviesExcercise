package com.ofek.moviesexcercise.data.movies

import com.ofek.moviesexcercise.data.objects.MovieDto
import com.ofek.moviesexcercise.data.objects.MovieRoom
import com.ofek.moviesexcercise.domain.objects.MovieObj
import org.json.JSONArray

/**
 * collection of mapping functions of movie objects
 */
object Mappers {

    fun mapMovieDtoToMovieObj(movieDto: MovieDto) : MovieObj {
        val movieObj = MovieObj()
        movieObj.genre = movieDto.genre.orEmpty()
        movieObj.image = movieDto.image.orEmpty()
        movieObj.title = movieDto.title.orEmpty()
        movieObj.rating = movieDto.rating.let { it!! }
        movieObj.releaseYear  = movieDto.releaseYear.let { it!! }
        return movieObj
    }

    fun mapMovieObjToMovieRoom(movieObj: MovieObj) : MovieRoom {
        // room doesn't store lists, thus it's necessary to convert it to string
        val genreAsJson = JSONArray()
        repeat(movieObj.genre!!.size) {
            genreAsJson.put(movieObj.genre!![it])
        }
        return MovieRoom(movieObj.image,movieObj.rating,genreAsJson.toString(),movieObj.title,movieObj.releaseYear)
    }

    fun mapRoomMovieToMovieObj(movieRoom: MovieRoom ) : MovieObj {
        val movieObj = MovieObj()
        val genres = ArrayList<String>()
        val genresAsJson =  JSONArray(movieRoom.genre)
        // converting back the genres json to list
        for (i in 0 until genresAsJson.length()) {
            genres.add(genresAsJson.getString(i))
        }
        movieObj.image = movieRoom.image
        movieObj.title = movieRoom.title
        movieObj.rating = movieRoom.rating
        movieObj.releaseYear  = movieRoom.releaseYear
        movieObj.genre = genres
        return movieObj
    }

}