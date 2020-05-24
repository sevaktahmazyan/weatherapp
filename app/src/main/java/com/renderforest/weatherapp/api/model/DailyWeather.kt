package com.renderforest.weatherapp.api.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import com.renderforest.weatherapp.R
import com.renderforest.weatherapp.api.response.WeatherResponse
import com.renderforest.weatherapp.base.interfaces.Adaptable

@Entity(tableName = "daily_weather")
class DailyWeather : Weather(), Adaptable {

    @SerializedName("sunrise")
    var sunrise: Long? = null

    @SerializedName("sunset")
    var sunset: Long? = null

    @SerializedName("temp")
    @Ignore
    var temperature: DailyTemperature? = null

    @SerializedName("feels_like")
    @Ignore
    var feelsLike: FeelsLike? = null

    @SerializedName("rain")
    var rain: Double? = null

    @ForeignKey(entity = WeatherResponse::class, parentColumns = ["dateTime"], childColumns = ["udid"], onDelete = ForeignKey.CASCADE)
    var udid: String? = null

    override fun getItemType(pageId: Int): Int {
        return R.layout.list_item_daily_weather
    }
}