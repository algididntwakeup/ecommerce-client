package com.bobrito.home.data.repository

import android.util.Log
import com.bobrito.home.data.model.Product
import com.bobrito.home.data.service.ProductService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductRepository {
    private val service: ProductService

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://ecommerce-gaiia-api.vercel.app/api/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ProductService::class.java)
    }

    suspend fun searchProducts(query: String): List<Product> {
        return try {
            Log.d("ProductRepository", "Searching for products with query: $query")
            val response = service.searchProducts(query)
            Log.d("ProductRepository", "Received ${response.data.size} products")
            response.data
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error searching products", e)
            throw e
        }
    }
}