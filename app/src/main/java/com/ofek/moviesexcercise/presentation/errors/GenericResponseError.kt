package com.ofek.moviesexcercise.presentation.errors

import com.ofek.moviesexcercise.presentation.errors.PresentationError.BasePresentationErrorHandler
import java.util.concurrent.atomic.AtomicBoolean

/**
 * this error will be handled by a view which implements it's handling protocol.
 * usually on a real project I would create more error types not just generic one,
 * so I could react to all necessary type of errors
 */
class GenericResponseError : PresentationError {
    // this boolean helps to verify this error handled only once
    private val isHandled =
        AtomicBoolean(false)

    override fun handle(handlingProtocol: BasePresentationErrorHandler) {
        // the view is passed to this function every time an error is thrown.
        // if the view implements the required protocol the callback on the view will be called and the view will react accordingly
        if (!isHandled.get() && handlingProtocol is GenericResponseErrorProtocol) {
            handlingProtocol.onGenericResponseError()
            isHandled.set(true)
        }
    }

    interface GenericResponseErrorProtocol : BasePresentationErrorHandler {
        fun onGenericResponseError()
    }
}