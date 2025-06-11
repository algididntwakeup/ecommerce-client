package com.bobrito.home.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobrito.home.data.model.Category
import com.bobrito.home.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoryUiState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val selectedCategory: Category? = null
)

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            repository.getCategories().collect { categories ->
                _uiState.update { currentState ->
                    currentState.copy(
                        categories = categories,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun selectCategory(category: Category) {
        _uiState.update { currentState ->
            currentState.copy(selectedCategory = category)
        }
    }

    fun clearSelection() {
        _uiState.update { currentState ->
            currentState.copy(selectedCategory = null)
        }
    }
} 