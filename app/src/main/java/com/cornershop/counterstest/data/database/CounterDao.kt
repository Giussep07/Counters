package com.cornershop.counterstest.data.database

import androidx.room.*
import com.cornershop.counterstest.data.database.model.CounterEntity

@Dao
interface CounterDao {

    @Query("SELECT * FROM counter")
    suspend fun getCounters(): List<CounterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCounters(counters: List<CounterEntity>)

    @Query("DELETE FROM counter WHERE id IN (:countersIds)")
    fun deleteCounters(countersIds: List<String>)
}