package com.renderforest.weatherapp.watherinfo.viewholders

import android.view.View
import com.renderforest.weatherapp.BuildConfig
import com.renderforest.weatherapp.R
import com.renderforest.weatherapp.api.model.HourlyWeather
import com.renderforest.weatherapp.base.constants.TemperatureUnit
import com.renderforest.weatherapp.base.interfaces.IBaseActionHandler
import com.renderforest.weatherapp.base.viewholders.BaseViewHolder
import com.renderforest.weatherapp.utils.PrefUtil
import com.renderforest.weatherapp.utils.Utils
import kotlinx.android.synthetic.main.list_item_hourly_weather.view.*
import java.text.SimpleDateFormat
import java.util.*

class HourlyWeatherViewHolder(view: View, actionHandler: IBaseActionHandler) : BaseViewHolder<IBaseActionHandler, HourlyWeather>(view, actionHandler) {

    val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    override fun bindItem(item: HourlyWeather) {
        super.bindItem(item)
        itemView.tv_time.text = dateFormat.format(item.dateTime * 1000)
        itemView.tv_humidity.text = "${item.humidity}%"
        itemView.tv_temperature.text = context.getString(
                R.string.hourly_weather_formatter,
                Utils.temperatureParser(item.temp, PrefUtil.tempUnit, TemperatureUnit.CELSIUS).toInt()
        )
        if (!item.weathersInfo.isNullOrEmpty()) {
            itemView.iv_hourly_tmp_icon.visibility = View.VISIBLE
            loadImage(itemView.iv_hourly_tmp_icon, String.format(BuildConfig.ICON_URL, item.weathersInfo!![0].icon))
        } else {
            itemView.iv_hourly_tmp_icon.visibility = View.INVISIBLE
        }
    }
}