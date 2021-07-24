package com.cornershop.counterstest.data.apiService

import com.cornershop.counterstest.data.model.response.CounterResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface CountersApi {
    @GET("v1/counters")
    suspend fun getCounters(): Response<List<CounterResponseModel>>
}