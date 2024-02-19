package com.example.starterproject.utils.api_handler.default_error_handler

import com.google.gson.reflect.TypeToken
import com.example.starterproject.utils.api_handler.exception.BadRequestException
import com.example.starterproject.utils.api_handler.exception.HandleableException
import com.example.starterproject.utils.api_handler.ErrorResponseHandler
import okhttp3.ResponseBody

/**
 * Response Error looks like this :
    {
        "meta": {
            "correlation_id": "m9h4hHc71QbTRCQ_mBZDp",
            "code": 400,
            "message": "Error Message Here" <= Use This for Default
        },
        "data": {},
        "errors": {
            "key": "error string"
        }
    }
 * */
class DefaultErrorResponseHandler: ErrorResponseHandler() {
    override fun handleBadRequestError(errorBody: ResponseBody): HandleableException {
        val type = object: TypeToken<DefaultErrorResponse>(){}.type
        val errorWrapper = errorBody.errorBodyParser<DefaultErrorResponse>(type)
        val errorMessage = errorWrapper?.meta?.message
        return BadRequestException(errorMessage.orEmpty())
    }
}