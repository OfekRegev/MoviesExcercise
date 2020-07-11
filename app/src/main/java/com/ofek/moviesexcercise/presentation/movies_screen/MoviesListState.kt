package com.ofek.moviesexcercise.presentation.movies_screen

import com.google.common.collect.ImmutableList
import com.ofek.moviesexcercise.presentation.objects.UiMovie

data class MoviesListState(
    val moviesList: ImmutableList<UiMovie>,
    val loading: Boolean,
    val currentPage: Int,
    val maxPage: Int?
) {
}
