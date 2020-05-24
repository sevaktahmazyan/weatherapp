package com.renderforest.weatherapp.watherinfo.viewholders

import android.text.SpannableString
import android.view.View
import androidx.core.content.ContextCompat
import com.renderforest.weatherapp.BuildConfig
import com.renderforest.weatherapp.R
import com.renderforest.weatherapp.api.model.DailyWeather
import com.renderforest.weatherapp.base.constants.TemperatureUnit
import com.renderforest.weatherapp.base.interfaces.IBaseActionHandler
import com.renderforest.weatherapp.base.viewholders.BaseViewHolder
import com.renderforest.weatherapp.utils.PrefUtil
import com.renderforest.weatherapp.utils.Utils
import kotlinx.android.synthetic.main.list_item_daily_weather.view.*
import java.text.SimpleDateFormat


class DailyWeatherViewHolder(view: View, actionHandler: IBaseActionHandler) : BaseViewHolder<IBaseActionHandler, DailyWeather>(view, actionHandler) {

    val dateFormat = SimpleDateFormat("EEE, d MMM")

    override fun bindItem(item: DailyWeather) {
        super.bindItem(item)
        itemView.tv_date.text = dateFormat.format(item.dateTime * 1000)
        itemView.tv_temperature.text = colored(
                context.getString(
                        R.string.daily_weather_formatter,
                        Utils.temperatureParser(item.temperature?.max ?: 0.0, PrefUtil.tempUnit, TemperatureUnit.CELSIUS).toInt(),
                        Utils.temperatureParser(item.temperature?.max ?: 0.0, PrefUtil.tempUnit, TemperatureUnit.CELSIUS).toInt()
                )
        )
        if (!item.weathersInfo.isNullOrEmpty()) {
            itemView.iv_daily_tmp_icon.visibility = View.VISIBLE
            loadImage(itemView.iv_daily_tmp_icon, String.format(BuildConfig.ICON_URL, item.weathersInfo!![0].icon))
        } else {
            itemView.iv_daily_tmp_icon.visibility = View.INVISIBLE
        }
    }

    private fun colored(text: String): SpannableString {

        val spannableString = SpannableString(text)
        if (!spannableString.contains("/")) {
            return spannableString
        }

        spannableString.setSpan(ContextCompat.getColor(context, R.color.secondaryTextColor), text.indexOf("/"), text.length, 0)

        return spannableString
    }
}