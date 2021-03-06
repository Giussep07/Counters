package com.cornershop.counterstest.di

import android.content.Context
import com.cornershop.counterstest.data.network.apiService.CountersApi
import com.cornershop.counterstest.presentation.utils.ConnectivityStatusManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.5:3000/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    fun provideCountersApiService(retrofit: Retrofit): CountersApi =
        retrofit.create(CountersApi::class.java)

    @Singleton
    @Provides
    fun provideConnectivityStatusManager(context: Context): ConnectivityStatusManager =
        ConnectivityStatusManager(context)
}
