package com.renderforest.weatherapp.watherinfo.datasource

import com.renderforest.weatherapp.api.ApiService
import com.renderforest.weatherapp.api.response.WeatherResponse
import io.reactivex.Observable
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(val apiService: ApiService) : WeatherDataSource {

    override fun getWeather(lat: Double, lng: Double): Observable<WeatherResponse> {
        return apiService.getWeather(lat, lng)
    }

    override fun saveWeather(weatherResponse: WeatherResponse): Observable<WeatherResponse> {
        return Observable.empty()
    }
}