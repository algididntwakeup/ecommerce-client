package com.bobrito.home.data.model

data class Product(
    val id: String,
    val productName: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val categoryId: String,
    val brandId: String,
    val createdAt: String,
    val updatedAt: String
)

data class ProductResponse(
    val status: String,
    val data: List<Product>
)