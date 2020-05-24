package com.renderforest.weatherapp.watherinfo

import com.renderforest.weatherapp.api.response.WeatherResponse
import com.renderforest.weatherapp.base.repository.BaseRepository
import com.renderforest.weatherapp.utils.Utils
import com.renderforest.weatherapp.watherinfo.datasource.WeatherDataSource
import com.renderforest.weatherapp.watherinfo.datasource.WeatherLocalDataSource
import com.renderforest.weatherapp.watherinfo.datasource.WeatherRemoteDataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WeatherRepository(remoteDataSource: WeatherRemoteDataSource, localDataSource: WeatherLocalDataSource) :
        BaseRepository<WeatherDataSource>(remoteDataSource, localDataSource) {

    fun getWeather(lat: Double, lng: Double, success: (r: WeatherResponse) -> Unit, error: (t: Throwable) -> Unit) {
        val dataSource = if (Utils.isNetworkAvailable()) {
            remoteDataSource.getWeather(lat, lng)
                    .flatMap { response -> saveWeather(response) }
        } else {
            localDataSource.getWeather(lat, lng)
        }
        add(
                dataSource
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(success, error)
        )
    }

    private fun saveWeather(weatherResponse: WeatherResponse): Observable<WeatherResponse> {
        return localDataSource.saveWeather(weatherResponse)
    }
}