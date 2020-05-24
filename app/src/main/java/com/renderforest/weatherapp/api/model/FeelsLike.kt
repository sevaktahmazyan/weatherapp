package com.renderforest.weatherapp.api.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "feels_like")
class FeelsLike : Temperature() {

    @ForeignKey(entity = DailyWeather::class, parentColumns = ["id"], childColumns = ["dateTime"], onDelete = ForeignKey.CASCADE)
    var dateTime: Long? = null
}