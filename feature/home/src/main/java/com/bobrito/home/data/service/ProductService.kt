package com.bobrito.home.data.service

import com.bobrito.home.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Header

interface ProductService {
    @GET("products")
    suspend fun searchProducts(
        @Query("productName") query: String,
        @Header("Authorization") token: String = "Bearer prakmobile"
    ): ProductResponse
}