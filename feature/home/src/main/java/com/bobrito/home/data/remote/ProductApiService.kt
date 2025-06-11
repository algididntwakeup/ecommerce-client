package com.bobrito.home.data.remote

import com.bobrito.home.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ProductApiService {
    @GET("api/v1/products")
    suspend fun getProducts(@Header("Authorization") token: String): ProductResponse
} 