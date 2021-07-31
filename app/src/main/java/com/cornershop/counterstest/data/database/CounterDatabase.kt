package com.cornershop.counterstest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cornershop.counterstest.data.database.model.CounterEntity

@Database(entities = [
    CounterEntity::class
], version = 1, exportSchema = false)
abstract class CounterDatabase : RoomDatabase() {

    abstract fun counterDao(): CounterDao
}