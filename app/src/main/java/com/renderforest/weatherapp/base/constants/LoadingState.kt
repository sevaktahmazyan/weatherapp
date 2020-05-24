package com.renderforest.weatherapp.base.constants

import androidx.annotation.IntDef


@IntDef(LoadingState.SHOW_LOADING, LoadingState.HIDE_LOADING, LoadingState.KEEP_LOADING)
annotation class LoadingState {
    companion object {
        const val SHOW_LOADING = 1
        const val HIDE_LOADING = 2
        const val KEEP_LOADING = 3
    }
}