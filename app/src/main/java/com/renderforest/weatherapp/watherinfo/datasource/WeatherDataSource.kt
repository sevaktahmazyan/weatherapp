package com.renderforest.weatherapp.watherinfo.datasource

import com.renderforest.weatherapp.api.response.WeatherResponse
import com.renderforest.weatherapp.base.datasource.DataSource
import io.reactivex.Observable

interface WeatherDataSource : DataSource {

    fun getWeather(lat: Double, lng: Double): Observable<WeatherResponse>

    fun saveWeather(weatherResponse: WeatherResponse): Observable<WeatherResponse>
}