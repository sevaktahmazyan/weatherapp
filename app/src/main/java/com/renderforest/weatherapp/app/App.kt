package com.renderforest.weatherapp.app

import android.content.Context
import com.renderforest.weatherapp.di.DaggerApplicationComponent
import com.renderforest.weatherapp.utils.PrefUtil
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        PrefUtil.init(context)
    }

    companion object {
        lateinit var context: Context
    }
}