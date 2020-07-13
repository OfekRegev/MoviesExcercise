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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PagingResult<*>

        if (result != other.result) return false
        if (page != other.page) return false
        if (maxPage != other.maxPage) return false

        return true
    }

    override fun hashCode(): Int {
        var result1 = result?.hashCode() ?: 0
        result1 = 31 * result1 + page
        result1 = 31 * result1 + maxPage
        return result1
    }
}