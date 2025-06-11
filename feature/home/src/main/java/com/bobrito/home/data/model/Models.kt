package com.bobrito.home.data.model

data class Product(
    val id: String,
    val productName: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val categoryId: String,
    val brandId: String,
    val createdAt: String,
    val updatedAt: String
)

data class ProductResponse(
    val success: Boolean,
    val data: List<Product>,
    val message: String,
    val error: String?
)

data class Category(
    val id: String,
    val name: String,
    val imageUrl: String,
    val createdAt: String,
    val updatedAt: String
)

data class CategoryResponse(
    val success: Boolean,
    val data: List<Category>,
    val message: String,
    val error: String?
)

data class CartItem(
    val id: String,
    val productId: String,
    val quantity: Int,
    val price: Double,
    val name: String,
    val imageUrl: String
)

data class Order(
    val id: String,
    val items: List<CartItem>,
    val totalPrice: Double,
    val status: String,
    val createdAt: String
) 