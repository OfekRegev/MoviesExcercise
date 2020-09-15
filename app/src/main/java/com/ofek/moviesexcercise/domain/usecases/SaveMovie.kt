package com.ofek.moviesexcercise.domain.usecases

import com.ofek.moviesexcercise.domain.common.BaseCompletableUseCase
import com.ofek.moviesexcercise.domain.repositories.MoviesRepository
import io.reactivex.Completable
import io.reactivex.CompletableTransformer

class SaveMovie(
    private val repository : MoviesRepository, transformer: CompletableTransformer
    ) : BaseCompletableUseCase(transformer) {


    override fun createSourceCompletable(params: Map<String?, Any?>?): Completable {
        TODO("Not yet implemented")
    }
}