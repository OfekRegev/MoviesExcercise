package com.ofek.moviesexcercise.domain.common;

import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.Single


abstract class BaseCompletableUseCase protected constructor(private val transformer: CompletableTransformer) {

    /**
     * applies transformer to the source stream
     * @param params the use case parameters
     * @return the transformed stream
     */
    protected fun createStream(params: Map<String, Any>?): Completable {
        return createSourceCompletable(params).compose(transformer)
    }

    /**
     * the method which provides the source stream
     * @param params the use case parameters
     * @return the source stream
     */
    protected abstract fun createSourceCompletable(params: Map<String, Any>?): Completable

}
