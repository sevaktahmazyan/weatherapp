package com.renderforest.weatherapp.watherinfo.datasource.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renderforest.weatherapp.api.model.DailyWeather

@Dao
interface DailyWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dailyWeather: List<DailyWeather>)

    @Query("SELECT * FROM daily_weather WHERE udid =:udid")
    fun get(udid: String): List<DailyWeather>

    @Query("DELETE FROM daily_weather")
    fun deleteAll()
}