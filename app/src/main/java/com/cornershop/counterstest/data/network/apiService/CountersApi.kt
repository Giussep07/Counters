package com.cornershop.counterstest.data.network.apiService

import com.cornershop.counterstest.data.network.model.request.CounterDecreaseRequestModel
import com.cornershop.counterstest.data.network.model.request.CounterIncreaseRequestModel
import com.cornershop.counterstest.data.network.model.request.CreateCounterRequestModel
import com.cornershop.counterstest.data.network.model.request.DeleteCounterRequestModel
import com.cornershop.counterstest.data.network.model.response.CounterResponseModel
import retrofit2.Response
import retrofit2.http.*

interface CountersApi {
    @GET("v1/counters")
    suspend fun getCounters(): List<CounterResponseModel>

    @POST("v1/counter/dec")
    suspend fun decreaseCounter(@Body counterDecreaseRequestModel: CounterDecreaseRequestModel): List<CounterResponseModel>

    @POST("v1/counter/inc")
    suspend fun increaseCounter(@Body counterIncreaseRequestModel: CounterIncreaseRequestModel): List<CounterResponseModel>

    @POST("v1/counter")
    suspend fun createCounter(@Body createCounterRequestModel: CreateCounterRequestModel): List<CounterResponseModel>

    @HTTP(method = "DELETE", path = "v1/counter", hasBody = true)
    suspend fun deleteCounter(@Body deleteCounterRequestModel: DeleteCounterRequestModel): List<CounterResponseModel>
}
