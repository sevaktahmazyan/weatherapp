package com.renderforest.weatherapp.api

import com.renderforest.weatherapp.api.response.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("2.5/onecall")
    fun getWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): Observable<WeatherResponse>
}