package com.renderforest.weatherapp.base.model

import com.google.gson.annotations.SerializedName

open class BaseResponse {

    @SerializedName("error")
    val hasError: Boolean = false

    @SerializedName("messages")
    val errors: List<ApiError>? = null
}
