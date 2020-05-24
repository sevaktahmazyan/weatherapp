package com.renderforest.weatherapp.di

import com.renderforest.weatherapp.watherinfo.WeatherRepository
import com.renderforest.weatherapp.watherinfo.datasource.WeatherLocalDataSource
import com.renderforest.weatherapp.watherinfo.datasource.WeatherRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(localDataSource: WeatherRemoteDataSource, remoteDataSource: WeatherLocalDataSource): WeatherRepository {
        return WeatherRepository(localDataSource, remoteDataSource)
    }
}