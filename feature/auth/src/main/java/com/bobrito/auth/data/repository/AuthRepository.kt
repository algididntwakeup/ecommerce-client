package com.bobrito.auth.data.repository

import com.bobrito.auth.data.model.Customer
import com.bobrito.auth.data.model.CustomerResponse
import com.bobrito.auth.data.model.LoginRequest
import com.bobrito.auth.data.remote.AuthApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://ecommerce-gaiia-api.vercel.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(AuthApiService::class.java)
    private val token = "Bearer prakmobile"

    suspend fun login(loginRequest: LoginRequest): Result<Customer> {
        return try {
            val response = apiService.login(loginRequest, token)
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.error ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(customer: Customer): Result<Customer> {
        return try {
            val response = apiService.register(customer, token)
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.error ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}