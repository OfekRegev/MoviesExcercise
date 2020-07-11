package com.ofek.moviesexcercise.domain.common;

import io.reactivex.Single
import io.reactivex.SingleTransformer


abstract class BaseSingleUseCase<T> protected constructor(private val transformer: SingleTransformer<T, T>) {

    /**
     * applies transformer to the source stream
     * @param params the use case parameters
     * @return the transformed stream
     */
    protected fun createStream(params: Map<String, Any>?): Single<T> {
        return createSourceSingle(params).compose(transformer)
    }

    /**
     * the method which provides the source stream
     * @param params the use case parameters
     * @return the source stream
     */
    protected abstract fun createSourceSingle(params: Map<String, Any>?): Single<T>

}
