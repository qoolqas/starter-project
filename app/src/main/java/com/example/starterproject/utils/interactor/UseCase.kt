package com.example.starterproject.utils.interactor

abstract class UseCase<Q : UseCase.RequestValues, P : UseCase.ResponseValues> {

    abstract fun execute(requestValues: Q): P

    /**
     * Data passed to a request.
     */
    interface RequestValues

    /**
     * Data received from a request.
     */
    interface ResponseValues
}