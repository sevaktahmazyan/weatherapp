package com.renderforest.weatherapp.base.viewholders

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.renderforest.weatherapp.base.interfaces.Adaptable
import com.renderforest.weatherapp.base.interfaces.IBaseActionHandler


abstract class BaseViewHolder<I : IBaseActionHandler, adaptableType : Adaptable>(item: View, handler: I? = null) : RecyclerView.ViewHolder(item) {

    var context: Context
    var displayWidth = 0
    var actionHandler: I? = null
    var displayHeight = 0
    var linearLayoutManager: LinearLayoutManager? = null

    lateinit var adaptable: adaptableType

    init {
        actionHandler = handler
        context = itemView.context
        displayWidth = context.resources.displayMetrics.widthPixels
        displayHeight = context.resources.displayMetrics.heightPixels
        itemView.setOnClickListener { actionHandler?.onItemClick(adaptable) }
    }

    open fun bindItem(item: adaptableType) {
        this.adaptable = item
    }

    open fun pixelOf(resId: Int): Int {
        return context.resources.getDimensionPixelOffset(resId)
    }

    open fun loadImage(view: ImageView, url: String?, placeHolder: Int? = null) {
        val builder = Glide.with(context)
                .load(url).apply(getRequestOption())

        placeHolder?.let { builder.error(it) }

        builder.into(view)
    }

    open fun getRequestOption(): RequestOptions {
        return RequestOptions().centerCrop()
    }
}