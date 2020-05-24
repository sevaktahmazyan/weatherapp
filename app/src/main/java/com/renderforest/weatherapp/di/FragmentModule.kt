package com.renderforest.weatherapp.di

import com.renderforest.weatherapp.watherinfo.WeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeWeatherFragment(): WeatherFragment

}