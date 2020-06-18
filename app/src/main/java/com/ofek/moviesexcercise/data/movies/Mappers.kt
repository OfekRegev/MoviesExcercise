package com.ofek.moviesexcercise.data.movies

import com.ofek.moviesexcercise.data.objects.MovieDto
import com.ofek.moviesexcercise.data.objects.MovieRoom
import com.ofek.moviesexcercise.domain.objects.MovieObj
import org.json.JSONArray
import org.json.JSONObject

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

    fun mapMovieObjToMovieRoom(it: MovieObj) : MovieRoom {
        // room doesn't store lists, thus it's necessary to convert it to string
        val genreAsJson = JSONArray()
        repeat(it.genre!!.size) {
            genreAsJson.put(it)
        }
        return MovieRoom(it.image,it.rating,genreAsJson.toString(),it.title,it.releaseYear)
    }

    fun mapRoomMovieToMovieObj(movieRoom: MovieRoom ) : MovieObj {
        val movieObj = MovieObj()
        val genres = ArrayList<String>()
        val genresAsJson =  JSONArray(movieRoom.genre)
        for (i in 0 until genresAsJson.length()) {
            genres.add(genresAsJson.getString(i))
        }
        movieObj.image = movieRoom.image
        movieObj.title = movieRoom.title
        movieObj.rating = movieRoom.rating
        movieObj.releaseYear  = movieRoom.releaseYear
        return movieObj
    }

}