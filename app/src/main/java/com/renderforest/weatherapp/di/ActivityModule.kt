package com.renderforest.weatherapp.di

import com.renderforest.weatherapp.watherinfo.WeatherActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeWeatherActivity(): WeatherActivity
}