package com.ofek.moviesexcercise.domain.common;

import io.reactivex.CompletableTransformer
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * this transformer applies an operator to provide the stream a thread pool to execute on
 */
object AsyncTransformers {

    /**
     * provides transformer for Single stream which applies a thread pool for async operations
     */
    fun <T>  getSingleTransformer() : SingleTransformer<T, T> {
        return SingleTransformer { upstream -> upstream.subscribeOn(Schedulers.io())}
    }

    /**
     * provides transformer for Single stream which applies a thread pool for async operations
     */
    fun getCompletableTransformer() : CompletableTransformer {
        return CompletableTransformer { upstream -> upstream.subscribeOn(Schedulers.io()) }
    }
}
