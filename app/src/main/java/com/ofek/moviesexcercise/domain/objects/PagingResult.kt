package com.ofek.moviesexcercise.domain.objects

/**
 * A wrapper class which represents a pagination result
 * created in order to easily keep the result synchronised with the page on the presentation side
 * @param <T> the result object type
</T> */
data class PagingResult<T> (
    val result: T ,
    val page : Int = 0,
    val maxPage : Int = 0
)