package com.cornershop.counterstest.di

import android.content.Context
import androidx.room.Room
import com.cornershop.counterstest.data.database.CounterDao
import com.cornershop.counterstest.data.database.CounterDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideCounterDatabase(context: Context): CounterDatabase {
        return Room.databaseBuilder(context, CounterDatabase::class.java, "counter_db")
            .build()
    }

    @Singleton
    @Provides
    fun provideCounterDao(counterDatabase: CounterDatabase): CounterDao {
        return counterDatabase.counterDao()
    }
}