package com.bobrito.home.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobrito.home.data.model.CartItem
import com.bobrito.home.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val totalPrice: Double = 0.0
)

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState

    init {
        loadCart()
    }

    private fun loadCart() {
        viewModelScope.launch {
            repository.getCart().collect { items ->
                _uiState.update { currentState ->
                    currentState.copy(
                        items = items,
                        isLoading = false,
                        totalPrice = items.sumOf { it.price * it.quantity }
                    )
                }
            }
        }
    }

    fun removeFromCart(itemId: String) {
        viewModelScope.launch {
            repository.removeFromCart(itemId)
            loadCart() // Reload cart after removal
        }
    }

    fun updateQuantity(itemId: String, quantity: Int) {
        _uiState.update { currentState ->
            val updatedItems = currentState.items.map { item ->
                if (item.id == itemId) {
                    item.copy(quantity = quantity)
                } else {
                    item
                }
            }
            currentState.copy(
                items = updatedItems,
                totalPrice = updatedItems.sumOf { it.price * it.quantity }
            )
        }
    }
} 