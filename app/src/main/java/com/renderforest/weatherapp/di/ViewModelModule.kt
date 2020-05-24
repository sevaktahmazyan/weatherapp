package com.renderforest.weatherapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.renderforest.weatherapp.watherinfo.viewmodel.WeatherActivityViewModel
import com.renderforest.weatherapp.watherinfo.viewmodel.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory?): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindWeatherViewModel(vm: WeatherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WeatherActivityViewModel::class)
    abstract fun bindWeatherActivityViewModel(vm: WeatherActivityViewModel): ViewModel
}