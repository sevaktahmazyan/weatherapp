package com.renderforest.weatherapp.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecorator(val space: Int, val orientation: Int = VERTICAL) : RecyclerView.ItemDecoration() {

    companion object {
        val VERTICAL = 1
        val HORIZONTAL = 2
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1) {
            if (orientation == VERTICAL) {
                outRect.bottom += space
            } else if (orientation == HORIZONTAL) {
                outRect.right += space
            }
        }
    }
}