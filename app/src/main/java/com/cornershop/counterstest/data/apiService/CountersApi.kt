package com.cornershop.counterstest.data.apiService

import com.cornershop.counterstest.data.model.request.CounterDecreaseRequestModel
import com.cornershop.counterstest.data.model.request.CounterIncreaseRequestModel
import com.cornershop.counterstest.data.model.request.CreateCounterRequestModel
import com.cornershop.counterstest.data.model.request.DeleteCounterRequestModel
import com.cornershop.counterstest.data.model.response.CounterResponseModel
import retrofit2.Response
import retrofit2.http.*

interface CountersApi {
    @GET("v1/counters")
    suspend fun getCounters(): Response<List<CounterResponseModel>>

    @POST("v1/counter/dec")
    suspend fun decreaseCounter(@Body counterDecreaseRequestModel: CounterDecreaseRequestModel): Response<List<CounterResponseModel>>

    @POST("v1/counter/inc")
    suspend fun increaseCounter(@Body counterIncreaseRequestModel: CounterIncreaseRequestModel): Response<List<CounterResponseModel>>

    @POST("v1/counter")
    suspend fun createCounter(@Body createCounterRequestModel: CreateCounterRequestModel): Response<List<CounterResponseModel>>

    @HTTP(method = "DELETE", path = "v1/counter", hasBody = true)
    suspend fun deleteCounter(@Body deleteCounterRequestModel: DeleteCounterRequestModel): Response<List<CounterResponseModel>>
}
