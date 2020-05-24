package com.renderforest.weatherapp.watherinfo.viewmodel

import com.renderforest.weatherapp.base.BaseViewModel
import com.renderforest.weatherapp.watherinfo.WeatherRepository
import javax.inject.Inject

class WeatherActivityViewModel @Inject constructor(repository: WeatherRepository) : BaseViewModel<WeatherRepository>(repository) {

}
