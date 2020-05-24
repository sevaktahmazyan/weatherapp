package com.renderforest.weatherapp.api.model


import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

open class Temperature {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = UUID.randomUUID().toString()

    @SerializedName("day")
    var day: Double = 0.0

    @SerializedName("night")
    var night: Double = 0.0

    @SerializedName("eve")
    var evening: Double = 0.0

    @SerializedName("morn")
    var morning: Double = 0.0
}