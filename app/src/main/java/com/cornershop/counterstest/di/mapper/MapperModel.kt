package com.cornershop.counterstest.di.mapper

import android.content.Context
import com.cornershop.counterstest.presentation.mapper.CounterPresentationMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapperModel {

    @Singleton
    @Provides
    fun provideCounterPresentationMapper(context: Context): CounterPresentationMapper = CounterPresentationMapper(context)
}
