package com.renderforest.weatherapp.watherinfo.datasource.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renderforest.weatherapp.api.model.DailyTemperature

@Dao
interface DailyTemperatureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(temperature: DailyTemperature): Long

    @Query("SELECT * FROM daily_temp  WHERE dateTime == :dateTime LIMIT 1")
    fun get(dateTime: Long): DailyTemperature

    @Query("DELETE FROM daily_temp")
    fun deleteAll()
}