package com.renderforest.weatherapp.watherinfo.datasource.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renderforest.weatherapp.api.model.FeelsLike

@Dao
interface FeelsLikeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dailyWeather: FeelsLike)

    @Query("SELECT * FROM feels_like WHERE dateTime == :dateTime LIMIT 1")
    fun get(dateTime: Long): FeelsLike

    @Query("DELETE FROM feels_like")
    fun deleteAll()
}