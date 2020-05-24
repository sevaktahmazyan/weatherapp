package com.renderforest.weatherapp.api.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName
import com.renderforest.weatherapp.R
import com.renderforest.weatherapp.api.response.WeatherResponse
import com.renderforest.weatherapp.base.interfaces.Adaptable

@Entity(tableName = "hourly_weather")
class HourlyWeather : Weather(), Adaptable {

    @SerializedName("feels_like")
    var feelsLike: Double? = null

    @SerializedName("temp")
    var temp: Double = 0.0

    @SerializedName("rain")
    @Ignore
    var rain: Rain? = null

    @ForeignKey(entity = WeatherResponse::class, parentColumns = ["dateTime"], childColumns = ["udid"], onDelete = CASCADE)
    var udid: String? = null

    override fun getItemType(pageId: Int): Int {
        return R.layout.list_item_hourly_weather
    }
}

@Entity(tableName = "rain")
class Rain {

    @SerializedName("1h")
    @PrimaryKey
    @ColumnInfo(name = "rain")
    var rain: Double = 0.0

    @ForeignKey(entity = HourlyWeather::class, parentColumns = ["rain"], childColumns = ["dateTime"], onDelete = CASCADE)
    var dateTime: Long? = null

}