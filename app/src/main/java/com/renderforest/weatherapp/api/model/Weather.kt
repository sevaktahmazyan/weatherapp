package com.renderforest.weatherapp.api.model

import androidx.room.ColumnInfo
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

open class Weather {

    @PrimaryKey
    @SerializedName("dt")
    @ColumnInfo(name = "dateTime")
    var dateTime: Long = 0

    @SerializedName("visibility")
    var visibility: Long? = null

    @SerializedName("pressure")
    var pressure: Int? = null

    @SerializedName("humidity")
    var humidity: Int? = null

    @SerializedName("dew_point")
    var dewPoint: Double? = null

    @SerializedName("wind_speed")
    var windSpeed: Double? = null

    @SerializedName("wind_deg")
    var windDegree: Int? = null

    @SerializedName("weather")
    @Ignore
    var weathersInfo: List<WeatherInfo>? = null

    @SerializedName("clouds")
    var clouds: Int? = null

    @SerializedName("uvi")
    var uvi: Double? = null

}