package com.ofek.moviesexcercise.domain.common

import io.reactivex.Single
import io.reactivex.SingleTransformer

/**
 * provides transformers which performs operation on the current thread
 * usually used for unit tests because unit tests most of the time should avoid running on background threads
 */
object SynchronousTransformers {


    fun <T> getSingleTransformer(): SingleTransformer<T?, T?> {
        return SingleTransformer { upstream: Single<T?>? -> upstream!! }
    }
}