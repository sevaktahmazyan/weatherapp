package com.renderforest.weatherapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.renderforest.weatherapp.BuildConfig
import com.renderforest.weatherapp.base.constants.TemperatureUnit

class PrefUtil {

    companion object {
        private lateinit var pref: SharedPreferences;

        fun init(context: Context) {
            pref = context.getSharedPreferences("${BuildConfig.APPLICATION_ID}_preferences", Context.MODE_PRIVATE)
        }

        var tempUnit: Int
            get() = pref.getInt("tempUnit", TemperatureUnit.KELVIN)
            set(value) = pref.edit().putInt("tempUnit", value).apply()
    }
}