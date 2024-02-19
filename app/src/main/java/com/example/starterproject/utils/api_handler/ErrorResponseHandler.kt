package com.example.starterproject.utils.api_handler

import com.google.gson.Gson
import com.example.starterproject.utils.api_handler.exception.HandleableException
import com.example.starterproject.utils.api_handler.exception.NotFoundException
import com.example.starterproject.utils.api_handler.exception.ServerErrorException
import com.example.starterproject.utils.api_handler.exception.UnauthorizedException
import okhttp3.ResponseBody
import java.lang.reflect.Type

abstract class ErrorResponseHandler {

    fun handle(errorBody: ResponseBody?, responseCode: Int): Exception {
        return fetchError(errorBody, responseCode)
    }

    protected open fun fetchError(errorBody: ResponseBody?, responseCode: Int): Exception {
        return try {
            val exception = when (responseCode) {
                ResponseCode.UNAUTHORIZED -> UnauthorizedException()
                ResponseCode.BAD_REQUEST -> {
                    errorBody?.let { error ->
                        handleBadRequestError(error)
                    } ?: Exception()
                }
                ResponseCode.NOT_FOUND -> NotFoundException()
                in 500..599 -> ServerErrorException()
                else -> Exception()
            }
            exception
        } catch (exception: Exception) {
            exception
        }
    }

    protected abstract fun handleBadRequestError(errorBody: ResponseBody): HandleableException

    protected fun <T> ResponseBody.errorBodyParser(type: Type): T? {
        return this.string().let {
            Gson().fromJson(it, type)
        }
    }
}