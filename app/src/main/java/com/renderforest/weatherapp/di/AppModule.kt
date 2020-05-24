package com.renderforest.weatherapp.di

import com.renderforest.weatherapp.app.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(app: App): App {
        return app
    }
}