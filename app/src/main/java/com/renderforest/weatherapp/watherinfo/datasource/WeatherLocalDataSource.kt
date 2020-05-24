package com.renderforest.weatherapp.watherinfo.datasource

import android.util.Log
import com.renderforest.weatherapp.api.response.WeatherResponse
import com.renderforest.weatherapp.watherinfo.datasource.dao.*
import io.reactivex.Observable
import javax.inject.Inject

class WeatherLocalDataSource @Inject constructor(
        val weatherDao: WeatherDao, val dailyTemperatureDao: DailyTemperatureDao,
        val weatherInfoDao: WeatherInfoDao, val dailyWeatherDao: DailyWeatherDao, val rainDao: RainDao, val hourlyWeatherDao: HourlyWeatherDao
        , val feelsLikeDao: FeelsLikeDao
) : WeatherDataSource {

    override fun getWeather(lat: Double, lng: Double): Observable<WeatherResponse> {
        Log.d("TAG", " get from local data source")
        return Observable.create { emitter ->
            if (!emitter.isDisposed) {
                var weatherResponse = weatherDao.get()
                weatherResponse?.let { response ->
                    val hourly = hourlyWeatherDao.get(response.udid)
                    hourly.map { it.rain = rainDao.get(it.dateTime) }
                    hourly.map { it.weathersInfo = weatherInfoDao.get(it.dateTime) }
                    response.hourly = hourly

                    val daily = dailyWeatherDao.get(response.udid)
                    daily.forEach {
                        it.temperature = dailyTemperatureDao.get(it.dateTime)
                        it.feelsLike = feelsLikeDao.get(it.dateTime)
                    }
                    response.daily = daily

                    response.current = hourlyWeatherDao.get(response.udid).firstOrNull()
                    response.current?.let {
                        it.weathersInfo = weatherInfoDao.get(it.dateTime)
                    }
                    emitter.onNext(response)
                } ?: kotlin.run {
                    weatherResponse = WeatherResponse()
                    weatherResponse!!.udid = ""
                    emitter.onNext(weatherResponse!!)
                }
            }
        }
    }

    override fun saveWeather(weatherResponse: WeatherResponse): Observable<WeatherResponse> {
        Log.d("TAG", " save weather")
        return Observable.create { emitter ->
            weatherDao.deleteAll()
            dailyTemperatureDao.deleteAll()

            weatherDao.insert(weatherResponse)
            weatherResponse.hourly?.let { hourlyWeather ->
                hourlyWeather.forEach { it.udid = weatherResponse.udid }
                val rainList = hourlyWeather.mapNotNull { it.rain }
                hourlyWeatherDao.insert(hourlyWeather)
                hourlyWeather.forEach {
                    it.weathersInfo?.forEach { info -> info.dateTime = it.dateTime }
                    weatherInfoDao.insert(it.weathersInfo)
                }
                rainDao.insert(rainList)
            }

            weatherResponse.daily?.let { daily ->
                daily.forEach { it.udid = weatherResponse.udid }
                dailyWeatherDao.insert(daily)
                daily.forEach {
                    it.feelsLike?.dateTime = it.dateTime
                    it.temperature?.dateTime = it.dateTime
                    it.feelsLike?.let { t -> feelsLikeDao.insert(t) }
                    it.temperature?.let { t -> dailyTemperatureDao.insert(t) }
                    it.weathersInfo?.forEach { info -> info.dateTime = it.dateTime }
                    weatherInfoDao.insert(it.weathersInfo)
                }
            }
            weatherResponse.current?.let {
                it.udid = weatherResponse.udid
                hourlyWeatherDao.insert(listOf(it))
                it.weathersInfo?.forEach { info -> info.dateTime = it.dateTime }
                weatherInfoDao.insert(it.weathersInfo)
            }

            if (!emitter.isDisposed) {
                emitter.onNext(weatherResponse)
            }
        }

    }
}