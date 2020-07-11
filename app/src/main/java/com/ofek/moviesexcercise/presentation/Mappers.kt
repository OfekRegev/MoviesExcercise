package com.ofek.moviesexcercise.presentation

import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.presentation.objects.UiMovie

object Mappers {

    fun mapMovieObjToUiMovie(movieObj: MovieObj): UiMovie {
        return UiMovie(
            movieObj.id,
            movieObj.title,
            movieObj.image,
            movieObj.rating,
            movieObj.releaseYear,
            movieObj.overview,
            movieObj.favorite
        )
    }
}