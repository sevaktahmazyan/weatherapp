package com.renderforest.weatherapp.di

import com.renderforest.weatherapp.api.ApiService
import com.renderforest.weatherapp.watherinfo.datasource.WeatherLocalDataSource
import com.renderforest.weatherapp.watherinfo.datasource.WeatherRemoteDataSource
import com.renderforest.weatherapp.watherinfo.datasource.dao.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Singleton
    @Provides
    fun provideWeatherRemoteDataSource(apiService: ApiService): WeatherRemoteDataSource {
        return WeatherRemoteDataSource(apiService)
    }

    @Singleton
    @Provides
    fun provideWeatherLocalDataSource(
            weatherDao: WeatherDao, dailyTemperatureDao: DailyTemperatureDao,
            weatherInfoDao: WeatherInfoDao, dailyWeatherDao: DailyWeatherDao, rainDao: RainDao, hourlyWeatherDao: HourlyWeatherDao
            , feelsLikeDao: FeelsLikeDao
    ): WeatherLocalDataSource {
        return WeatherLocalDataSource(weatherDao, dailyTemperatureDao, weatherInfoDao, dailyWeatherDao, rainDao, hourlyWeatherDao, feelsLikeDao)
    }
}