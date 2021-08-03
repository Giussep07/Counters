package com.cornershop.counterstest.di

import android.app.Application
import android.content.Context
import com.cornershop.counterstest.presentation.utils.ResourcesManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideApplication(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideResourcesManager(context: Context): ResourcesManager = ResourcesManager(context)
}