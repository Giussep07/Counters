package com.cornershop.counterstest.di

import com.cornershop.counterstest.presentation.home.HomeFragment
import com.cornershop.counterstest.presentation.welcome.WelcomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBindingModule {

    @ContributesAndroidInjector
    abstract fun contributeWelcomeFragment(): WelcomeFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment
}