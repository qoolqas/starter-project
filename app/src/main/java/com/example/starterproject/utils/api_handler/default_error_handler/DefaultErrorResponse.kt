package com.example.starterproject.utils.api_handler.default_error_handler

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Response Error looks like this :
    {
        "meta": {
            "correlation_id": "m9h4hHc71QbTRCQ_mBZDp",
            "code": 400,
            "message": "Error Message Here"
        },
        "data": {},
        "errors": {
            "key": "error string"
        }
    }
 *
 * errors field only used for custom, so ignore it here
 * */

data class DefaultErrorResponse(
    @Expose
    @SerializedName("status") val status: String? = null,
    @Expose
    @SerializedName("meta") val meta: MetaErrorResponse? = null
)

data class MetaErrorResponse(
    @Expose
    @SerializedName("correlation_id") val correlationId: String? = null,
    @Expose
    @SerializedName("code") val code: Int? = null,
    @Expose
    @SerializedName("message") val message: String? = null
)
