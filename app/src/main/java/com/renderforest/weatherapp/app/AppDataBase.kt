package com.renderforest.weatherapp.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.renderforest.weatherapp.api.model.*
import com.renderforest.weatherapp.api.response.WeatherResponse
import com.renderforest.weatherapp.watherinfo.datasource.dao.*


@Database(
        entities = [WeatherResponse::class, WeatherInfo::class, Rain::class,
            DailyWeather::class, HourlyWeather::class, FeelsLike::class, DailyTemperature::class], version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDao

    abstract fun getDailyTemperatureDao(): DailyTemperatureDao

    abstract fun getWeatherInfoDao(): WeatherInfoDao

    abstract fun getDailyWeatherDao(): DailyWeatherDao

    abstract fun getRainDao(): RainDao

    abstract fun getHourlyWeatherDao(): HourlyWeatherDao

    abstract fun getFeelsLikeDao(): FeelsLikeDao
}