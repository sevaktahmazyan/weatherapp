package com.renderforest.weatherapp.base.model

import com.google.gson.annotations.SerializedName

class ApiError {

    @SerializedName("key")
    var key: String = ""

    @SerializedName("error")
    var error: String = ""
}