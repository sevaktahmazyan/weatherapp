package DataAdapter.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.renderforest.weatherapp.R
import com.renderforest.weatherapp.base.interfaces.Adaptable
import com.renderforest.weatherapp.base.interfaces.IBaseActionHandler
import com.renderforest.weatherapp.base.viewholders.BaseViewHolder
import com.renderforest.weatherapp.base.viewholders.EmptyViewHolder
import com.renderforest.weatherapp.watherinfo.viewholders.DailyWeatherViewHolder
import com.renderforest.weatherapp.watherinfo.viewholders.HourlyWeatherViewHolder

open class DataAdapter private constructor(viewHolderInterface: IBaseActionHandler, private var pageId: Int, data: ArrayList<Adaptable>?) : RecyclerView.Adapter<BaseViewHolder<*, Adaptable>>() {


    var data: ArrayList<Adaptable> = ArrayList<Adaptable>()
    private var actionHandler: IBaseActionHandler = viewHolderInterface

    constructor(viewHolderInterface: IBaseActionHandler, pageId: Int) : this(viewHolderInterface, pageId, ArrayList()) {
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].getItemType(pageId)
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): BaseViewHolder<*, Adaptable> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.list_item_hourly_weather -> HourlyWeatherViewHolder(view, actionHandler)
            R.layout.list_item_daily_weather -> DailyWeatherViewHolder(view, actionHandler)
            else -> EmptyViewHolder(view, actionHandler)
        } as BaseViewHolder<*, Adaptable>
    }

    override fun onBindViewHolder(@NonNull holder: BaseViewHolder<*, Adaptable>, position: Int) {
        holder.bindItem(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setItems(items: List<Adaptable>?) {
        if (items == null) {
            return
        }
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    //TODO add another necessary methods

    init {
        if (data != null) {
            this.data.addAll(data)
        }
    }
}