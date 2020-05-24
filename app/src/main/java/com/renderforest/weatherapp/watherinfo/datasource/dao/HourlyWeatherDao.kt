package com.renderforest.weatherapp.watherinfo.datasource.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renderforest.weatherapp.api.model.HourlyWeather


@Dao
interface HourlyWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(hourlyWeather: List<HourlyWeather>)

    @Query("SELECT * FROM hourly_weather WHERE udid == :udid")
    fun get(udid: String): List<HourlyWeather>

    @Query("DELETE FROM hourly_weather")
    fun deleteAll()
}