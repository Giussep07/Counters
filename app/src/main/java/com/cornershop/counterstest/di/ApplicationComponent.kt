package com.cornershop.counterstest.di

import android.app.Application
import com.cornershop.counterstest.app.CountersTestApp
import com.cornershop.counterstest.di.viewModel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    FragmentsBindingModule::class,
    ViewModelModule::class
])
interface ApplicationComponent : AndroidInjector<CountersTestApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}