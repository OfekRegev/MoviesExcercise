package com.ofek.moviesexcercise.presentation.errors

/**
 *
 * see PresentationError implementation for more information about how the error interface works
 */
interface PresentationError {
    fun handle(handlingProtocol: BasePresentationErrorHandler)
    interface BasePresentationErrorHandler
}