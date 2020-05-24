package com.renderforest.weatherapp.watherinfo

import DataAdapter.base.adapter.DataAdapter
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.renderforest.weatherapp.BuildConfig
import com.renderforest.weatherapp.R
import com.renderforest.weatherapp.api.model.DailyWeather
import com.renderforest.weatherapp.api.model.HourlyWeather
import com.renderforest.weatherapp.api.response.WeatherResponse
import com.renderforest.weatherapp.base.constants.Pages
import com.renderforest.weatherapp.base.constants.TemperatureUnit
import com.renderforest.weatherapp.base.fragment.BaseFragmentWithViewModel
import com.renderforest.weatherapp.base.interfaces.IBaseActionHandler
import com.renderforest.weatherapp.utils.PrefUtil
import com.renderforest.weatherapp.utils.SpaceItemDecorator
import com.renderforest.weatherapp.utils.Utils
import com.renderforest.weatherapp.watherinfo.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_weather.*
import org.greenrobot.eventbus.Subscribe
import pub.devrel.easypermissions.EasyPermissions


class WeatherFragment : BaseFragmentWithViewModel<WeatherViewModel>() {

    private val hourlyAdapter = DataAdapter(object : IBaseActionHandler {

    }, Pages.PAGE_ID_WEATHER)

    private val dailyAdapter = DataAdapter(object : IBaseActionHandler {

    }, Pages.PAGE_ID_WEATHER)

    override fun getViewModelClass(): Class<WeatherViewModel> {
        return WeatherViewModel::class.java
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_weather
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.weatherLiveData.observe(this, Observer {
            if (it.udid.isNotEmpty()) {
                bindWeatherData(it)
            } else {
                AlertDialog.Builder(context)
                        .setTitle("INFO")
                        .setMessage("Please check internet connection")
                        .setNegativeButton(android.R.string.cancel) { dialog, p1 -> dialog.dismiss() }
                        .setPositiveButton(android.R.string.ok) { dialog, p1 ->
                            dialog.dismiss()
                            try {
                                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                            } catch (e: ActivityNotFoundException) {
                                e.printStackTrace()
                            }
                        }.show()
            }
        })
    }

    private fun bindWeatherData(weatherResponse: WeatherResponse) {
        initDailyWeatherList(weatherResponse.daily)
        initHourlyWeatherList(weatherResponse.hourly)
        tv_city.text = weatherResponse.timezone
        weatherResponse.current?.let {
            bindCurrentWeather(it)
        }
    }

    private fun bindCurrentWeather(current: HourlyWeather) {
        tv_current_temperature.text = "${Utils.temperatureParser(current.temp, PrefUtil.tempUnit, TemperatureUnit.CELSIUS).toInt()}"
        tv_weather_sign.visibility = View.VISIBLE
        tv_city.visibility = View.VISIBLE
        if (!current.weathersInfo.isNullOrEmpty()) {
            tv_current_tmp_description.visibility = View.VISIBLE
            tv_current_tmp_description.text = current.weathersInfo!![0].main
            Glide.with(context!!)
                    .load(String.format(BuildConfig.ICON_URL, current.weathersInfo!![0].icon))
                    .apply(RequestOptions().centerCrop())
                    .into(iv_current_tmp_icon)
        } else {
            tv_current_tmp_description.visibility = View.INVISIBLE
        }
    }

    private fun initHourlyWeatherList(hourly: List<HourlyWeather>?) {
        rv_hourly.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_hourly.addItemDecoration(SpaceItemDecorator(pixelOf(R.dimen.hourly_item_space), SpaceItemDecorator.HORIZONTAL))
        rv_hourly.adapter = hourlyAdapter
        hourlyAdapter.setItems(hourly)
    }

    private fun initDailyWeatherList(daily: List<DailyWeather>?) {
        rv_daily.layoutManager = LinearLayoutManager(context)
        rv_daily.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv_daily.adapter = dailyAdapter
        dailyAdapter.setItems(daily)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tryRetrievingWeatherData()
    }

    private fun tryRetrievingWeatherData() {
        if (EasyPermissions.hasPermissions(context!!, *getBaseActivity()?.locationPerms!!) && Utils.isGPSAvailable()) {
            getBaseActivity()?.requestLocation()
        } else {
            getBaseActivity()?.lasKnownLocation()?.let {
                vm.getWeather(it.latitude, it.longitude)
            } ?: kotlin.run {
                getBaseActivity()?.getLocation()
            }
        }
    }


    @Subscribe
    fun onLocationReceived(location: Location) {
        println("location available")
        vm.getWeather(location.latitude, location.longitude)
    }
}