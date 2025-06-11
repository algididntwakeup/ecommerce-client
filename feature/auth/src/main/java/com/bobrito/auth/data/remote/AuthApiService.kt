package com.bobrito.auth.data.remote

import com.bobrito.auth.data.model.Customer
import com.bobrito.auth.data.model.CustomerResponse
import com.bobrito.auth.data.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {
    @GET("api/v1/customers")
    suspend fun getCustomers(@Header("Authorization") token: String = "Bearer prakmobile"): List<Customer>

    @POST("api/v1/customers/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
        @Header("Authorization") token: String = "Bearer prakmobile"
    ): CustomerResponse

    @POST("api/v1/customers/register")
    suspend fun register(
        @Body customer: Customer,
        @Header("Authorization") token: String = "Bearer prakmobile"
    ): CustomerResponse
}