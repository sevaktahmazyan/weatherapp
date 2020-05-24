package com.renderforest.weatherapp.api.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather_info")
class WeatherInfo {

    @SerializedName("id")
    var id: Long = 0

    @SerializedName("main")
    var main: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("icon")
    var icon: String? = null

    @ForeignKey(entity = HourlyWeather::class, parentColumns = ["dateTime"], childColumns = ["dateTime"], onDelete = ForeignKey.CASCADE)
    @PrimaryKey
    @ColumnInfo(name = "dateTime")
    var dateTime: Long? = null
}