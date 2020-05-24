package com.renderforest.weatherapp.watherinfo

import android.os.Bundle
import com.renderforest.weatherapp.R
import com.renderforest.weatherapp.base.activity.BaseActivityWithViewModel
import com.renderforest.weatherapp.watherinfo.viewmodel.WeatherActivityViewModel

class WeatherActivity : BaseActivityWithViewModel<WeatherActivityViewModel>() {

    override fun getLayoutResId(): Int {
        return R.layout.activity_weather
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getViewModelClass(): Class<WeatherActivityViewModel> {
        return WeatherActivityViewModel::class.java
    }
}
