package com.renderforest.weatherapp.watherinfo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.renderforest.weatherapp.api.response.WeatherResponse
import com.renderforest.weatherapp.base.BaseViewModel
import com.renderforest.weatherapp.watherinfo.WeatherRepository
import javax.inject.Inject

class WeatherViewModel @Inject constructor(repository: WeatherRepository) : BaseViewModel<WeatherRepository>(repository) {


    private val weatherMutableLiveData = MutableLiveData<WeatherResponse>()
    val weatherLiveData: LiveData<WeatherResponse> = weatherMutableLiveData

    fun getWeather(lat: Double, lng: Double) {
        repository.getWeather(lat, lng, this::onWeatherDataReceived, this::onError)
    }

    private fun onWeatherDataReceived(response: WeatherResponse) {
        weatherMutableLiveData.postValue(response)
        Log.d("TAG", Gson().toJson(response))
    }
}