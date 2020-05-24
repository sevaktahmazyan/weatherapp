package com.renderforest.weatherapp.watherinfo.datasource.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renderforest.weatherapp.api.model.Rain

@Dao
interface RainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rain: List<Rain>)

    @Query("SELECT * FROM rain WHERE dateTime = :dateTime LIMIT 1")
    fun get(dateTime: Long): Rain

    @Query("DELETE FROM rain")
    fun deleteAll()
}