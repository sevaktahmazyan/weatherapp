package com.renderforest.weatherapp.api.model

import androidx.room.Entity
import androidx.room.ForeignKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "daily_temp")
class DailyTemperature : Temperature() {

    @SerializedName("min")
    var min: Double = 0.0

    @SerializedName("max")
    var max: Double = 0.0

    @ForeignKey(entity = DailyWeather::class, parentColumns = ["id"], childColumns = ["dateTime"], onDelete = ForeignKey.CASCADE)
    var dateTime: Long? = null
}