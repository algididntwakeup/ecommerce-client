package com.bobrito.home.data.repository

import android.util.Log
import com.bobrito.home.data.model.Product
import com.bobrito.home.data.model.ProductResponse
import com.bobrito.home.data.remote.ProductApiService
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