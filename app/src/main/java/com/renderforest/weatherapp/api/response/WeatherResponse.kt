package com.renderforest.weatherapp.api.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.renderforest.weatherapp.api.model.DailyWeather
import com.renderforest.weatherapp.api.model.HourlyWeather
import java.util.*

@Entity(tableName = "weather_response")
class WeatherResponse {

    @PrimaryKey
    @ColumnInfo(name = "udid")
    var udid: String = UUID.randomUUID().toString()

    @SerializedName("lat")
    var lat: Double? = null

    @SerializedName("lon")
    var lon: Double? = null

    @SerializedName("timezone")
    var timezone: String? = null

    @SerializedName("timezone_offset")
    var timezoneOffset: Long? = null

    @SerializedName("current")
    @Ignore
    var current: HourlyWeather? = null

    @SerializedName("hourly")
    @Ignore
    var hourly: List<HourlyWeather>? = null

    @SerializedName("daily")
    @Ignore
    var daily: List<DailyWeather>? = null
}