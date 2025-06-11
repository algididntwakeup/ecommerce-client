package com.bobrito.home.ui.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobrito.home.data.model.Product
import com.bobrito.home.data.repository.ProductRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repository = ProductRepository()

    var searchQuery by mutableStateOf("")
        private set

    var searchResults by mutableStateOf<List<Product>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    private var searchJob: Job? = null

    init {
        // Load initial products
        searchProducts("")
    }

    fun onSearchQueryChange(query: String) {
        searchQuery = query
        searchProducts(query)
    }

    private fun searchProducts(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            try {
                delay(300) // Debounce search
                isLoading = true
                error = null

                Log.d("SearchViewModel", "Searching for: $query")
                searchResults = repository.searchProducts(query)
                Log.d("SearchViewModel", "Found ${searchResults.size} results")

            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error searching products", e)
                error = "Failed to load products: ${e.message}"
                searchResults = emptyList()
            } finally {
                isLoading = false
            }
        }
    }
}