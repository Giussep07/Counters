package com.cornershop.counterstest.data.repository

import com.cornershop.counterstest.data.remoteSource.home.HomeRemoteDataSource
import com.cornershop.counterstest.domain.repository.HomeRepository
import com.cornershop.counterstest.presentation.state.HomeUiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeRemoteDataSource: HomeRemoteDataSource): HomeRepository {

    override suspend fun getCounters(): Flow<HomeUiState> {
        return homeRemoteDataSource.getCounters()
    }
}