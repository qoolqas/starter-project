package com.example.starterproject.utils.api_handler

import com.example.starterproject.utils.api_handler.default_error_handler.DefaultErrorResponseHandler
import retrofit2.Call
import retrofit2.Response

object ApiHandler {

    suspend fun <T: Any> handleApi(
        errorHandler: ErrorResponseHandler = DefaultErrorResponseHandler(),
        block: suspend () -> Response<T>
    ): T? {
        try {
            val response = block.invoke()

            if (response.isSuccessful) {
                return response.body()
            }

            // When Error
            throw errorHandler.handle(response.errorBody(), response.code())
        } catch (e: Exception) {
            throw e
        }
    }

    fun <T: Any> handleApiSynchronously(
        errorHandler: ErrorResponseHandler = DefaultErrorResponseHandler(),
        block: () -> Call<T>
    ): T? {
        try {
            val response = block.invoke().execute()

            if (response.isSuccessful) {
                return response.body()
            }

            // When Error
            throw errorHandler.handle(response.errorBody(), response.code())
        } catch (e: Exception) {
            throw e
        }
    }
}