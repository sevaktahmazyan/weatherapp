package com.renderforest.weatherapp.di

import androidx.room.Room
import com.renderforest.weatherapp.app.App
import com.renderforest.weatherapp.app.AppDataBase
import com.renderforest.weatherapp.watherinfo.datasource.dao.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DBModule {

    @Singleton
    @Provides
    fun provideDataBase(): AppDataBase {
        return Room.databaseBuilder(App.context, AppDataBase::class.java, "weather.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(dataBase: AppDataBase): WeatherDao {
        return dataBase.getWeatherDao()
    }

    @Singleton
    @Provides
    fun provideTemperatureDao(dataBase: AppDataBase): DailyTemperatureDao {
        return dataBase.getDailyTemperatureDao()
    }

    @Singleton
    @Provides
    fun provideWeatherInfoDao(dataBase: AppDataBase): WeatherInfoDao {
        return dataBase.getWeatherInfoDao()
    }

    @Singleton
    @Provides
    fun provideDailyWeatherDao(dataBase: AppDataBase): DailyWeatherDao {
        return dataBase.getDailyWeatherDao()
    }

    @Singleton
    @Provides
    fun provideHourlyWeatherDao(dataBase: AppDataBase): HourlyWeatherDao {
        return dataBase.getHourlyWeatherDao()
    }

    @Singleton
    @Provides
    fun provideRainDao(dataBase: AppDataBase): RainDao {
        return dataBase.getRainDao()
    }

    @Singleton
    @Provides
    fun provideFeelsLikeDao(dataBase: AppDataBase): FeelsLikeDao {
        return dataBase.getFeelsLikeDao()
    }
}