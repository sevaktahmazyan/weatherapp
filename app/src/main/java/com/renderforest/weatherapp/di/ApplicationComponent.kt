package com.renderforest.weatherapp.di

import android.content.Context
import com.renderforest.weatherapp.app.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
        modules = [AndroidInjectionModule::class,
            NetworkModule::class,
            ViewModelModule::class,
            RepositoryModule::class,
            DataSourceModule::class,
            DBModule::class,
            FragmentModule::class,
            ActivityModule::class]
)
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): ApplicationComponent
    }

    override fun inject(instance: App)
}