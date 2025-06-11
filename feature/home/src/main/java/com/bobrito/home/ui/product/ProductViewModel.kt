package com.bobrito.home.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobrito.home.data.model.Product
import com.bobrito.home.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductUiState(
    val products: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val searchQuery: String = "",
    val selectedPriceRange: PriceRange? = null,
    val sortOrder: SortOrder = SortOrder.NONE
)

enum class PriceRange(val min: Int, val max: Int) {
    RANGE_100K_1M(100_000, 1_000_000),
    RANGE_1M_2M(1_000_000, 2_000_000),
    RANGE_2M_3M(2_000_000, 3_000_000),
    RANGE_3M_4M(3_000_000, 4_000_000),
    RANGE_4M_5M(4_000_000, 5_000_000)
}

enum class SortOrder {
    NONE,
    LOWEST_PRICE,
    HIGHEST_PRICE
}

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            repository.getProducts().collect { products ->
                _uiState.update { currentState ->
                    currentState.copy(
                        products = products,
                        filteredProducts = filterAndSortProducts(
                            products,
                            currentState.searchQuery,
                            currentState.selectedPriceRange,
                            currentState.sortOrder
                        ),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = query,
                filteredProducts = filterAndSortProducts(
                    currentState.products,
                    query,
                    currentState.selectedPriceRange,
                    currentState.sortOrder
                )
            )
        }
    }

    fun updatePriceRange(range: PriceRange?) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedPriceRange = range,
                filteredProducts = filterAndSortProducts(
                    currentState.products,
                    currentState.searchQuery,
                    range,
                    currentState.sortOrder
                )
            )
        }
    }

    fun updateSortOrder(order: SortOrder) {
        _uiState.update { currentState ->
            currentState.copy(
                sortOrder = order,
                filteredProducts = filterAndSortProducts(
                    currentState.products,
                    currentState.searchQuery,
                    currentState.selectedPriceRange,
                    order
                )
            )
        }
    }

    private fun filterAndSortProducts(
        products: List<Product>,
        query: String,
        priceRange: PriceRange?,
        sortOrder: SortOrder
    ): List<Product> {
        var filtered = if (query.isNotEmpty()) {
            repository.searchProducts(query, products)
        } else {
            products
        }

        if (priceRange != null) {
            filtered = filtered.filter { product ->
                product.price in priceRange.min..priceRange.max
            }
        }

        return when (sortOrder) {
            SortOrder.LOWEST_PRICE -> filtered.sortedBy { it.price }
            SortOrder.HIGHEST_PRICE -> filtered.sortedByDescending { it.price }
            SortOrder.NONE -> filtered
        }
    }
} 