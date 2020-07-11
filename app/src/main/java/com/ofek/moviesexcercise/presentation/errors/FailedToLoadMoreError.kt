package com.ofek.moviesexcercise.presentation.errors

import java.util.concurrent.atomic.AtomicBoolean

class FailedToLoadMoreError : PresentationError {
    // a toggle boolean to validate the error handled only once
    private val handled = AtomicBoolean(false)
    override fun handle(handlingProtocol: PresentationError.BasePresentationErrorHandler) {
        if (!handled.get() && handlingProtocol is HandlingProtocol ) {
            handlingProtocol.onFailedToLoadMore()
        }
    }

    interface HandlingProtocol {
        fun onFailedToLoadMore()
    }
}
