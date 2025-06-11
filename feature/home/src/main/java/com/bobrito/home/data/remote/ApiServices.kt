package com.bobrito.home.data.remote

import com.bobrito.home.data.model.*
import retrofit2.http.*

interface ProductApiService {
    @GET("api/v1/products")
    suspend fun getProducts(@Header("Authorization") token: String): ProductResponse

    @GET("api/v1/products/{id}")
    suspend fun getProduct(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Product
}

interface CategoryApiService {
    @GET("api/v1/categories")
    suspend fun getCategories(@Header("Authorization") token: String): CategoryResponse

    @GET("api/v1/categories/{id}")
    suspend fun getCategory(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Category
}

interface CartApiService {
    @GET("api/v1/cart")
    suspend fun getCart(@Header("Authorization") token: String): List<CartItem>

    @POST("api/v1/cart")
    suspend fun addToCart(
        @Body cartItem: CartItem,
        @Header("Authorization") token: String
    ): CartItem

    @DELETE("api/v1/cart/{id}")
    suspend fun removeFromCart(
        @Path("id") id: String,
        @Header("Authorization") token: String
    )
}

interface OrderApiService {
    @GET("api/v1/orders")
    suspend fun getOrders(@Header("Authorization") token: String): List<Order>

    @POST("api/v1/orders")
    suspend fun createOrder(
        @Body order: Order,
        @Header("Authorization") token: String
    ): Order
} 