package com.renderforest.weatherapp.watherinfo.datasource.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renderforest.weatherapp.api.model.WeatherInfo

@Dao
interface WeatherInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherInfo: List<WeatherInfo>?): List<Long>

    @Query("SELECT * FROM weather_info WHERE dateTime = :dateTime")
    fun get(dateTime: Long): List<WeatherInfo>?

    @Query("DELETE FROM weather_info")
    fun deleteAll()
}