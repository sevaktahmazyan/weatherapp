package com.renderforest.weatherapp.base.constants

import androidx.annotation.IntDef

@IntDef(TemperatureUnit.CELSIUS, TemperatureUnit.KELVIN, TemperatureUnit.FAHRENHEIT)
annotation class TemperatureUnit {
    companion object {
        const val KELVIN = 1
        const val CELSIUS = 2
        const val FAHRENHEIT = 3
    }
}