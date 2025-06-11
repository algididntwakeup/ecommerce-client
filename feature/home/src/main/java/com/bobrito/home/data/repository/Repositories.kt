package com.bobrito.home.data.repository

import com.bobrito.home.data.model.*
import com.bobrito.home.data.remote.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ProductApiService
) {
    fun getProducts(): Flow<List<Product>> = flow {
        try {
            val response = apiService.getProducts("Bearer prakmobile")
            if (response.success) {
                emit(response.data)
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    fun searchProducts(query: String, products: List<Product>): List<Product> {
        return if (query.isEmpty()) {
            products
        } else {
            products.filter { product ->
                product.productName.contains(query, ignoreCase = true) ||
                        product.description.contains(query, ignoreCase = true)
            }
        }
    }
}

class CategoryRepository @Inject constructor(
    private val apiService: CategoryApiService
) {
    fun getCategories(): Flow<List<Category>> = flow {
        try {
            val response = apiService.getCategories("Bearer prakmobile")
            if (response.success) {
                emit(response.data)
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}

class CartRepository @Inject constructor(
    private val apiService: CartApiService
) {
    fun getCart(): Flow<List<CartItem>> = flow {
        try {
            val items = apiService.getCart("Bearer prakmobile")
            emit(items)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    suspend fun addToCart(cartItem: CartItem) {
        try {
            apiService.addToCart(cartItem, "Bearer prakmobile")
        } catch (e: Exception) {
            // Handle error
        }
    }

    suspend fun removeFromCart(id: String) {
        try {
            apiService.removeFromCart(id, "Bearer prakmobile")
        } catch (e: Exception) {
            // Handle error
        }
    }
}

class OrderRepository @Inject constructor(
    private val apiService: OrderApiService
) {
    fun getOrders(): Flow<List<Order>> = flow {
        try {
            val orders = apiService.getOrders("Bearer prakmobile")
            emit(orders)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    suspend fun createOrder(order: Order): Order? {
        return try {
            apiService.createOrder(order, "Bearer prakmobile")
        } catch (e: Exception) {
            null
        }
    }
} 