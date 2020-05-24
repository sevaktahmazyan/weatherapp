package com.renderforest.weatherapp.watherinfo.datasource.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renderforest.weatherapp.api.response.WeatherResponse

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherResponse: WeatherResponse)

    @Query("SELECT * FROM weather_response LIMIT 1")
    fun get(): WeatherResponse?

    @Query("DELETE FROM weather_response")
    fun deleteAll()
}