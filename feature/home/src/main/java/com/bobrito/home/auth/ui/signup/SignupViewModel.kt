package com.bobrito.home.auth.ui.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobrito.home.auth.data.model.Customer
import com.bobrito.home.auth.data.repository.AuthRepository
import kotlinx.coroutines.launch
import java.util.UUID

class SignupViewModel : ViewModel() {
    private val repository = AuthRepository()

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var numberPhone by mutableStateOf("")
    var address by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var customer by mutableStateOf<Customer?>(null)

    fun onNameChange(newName: String) {
        name = newName
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onNumberPhoneChange(newNumberPhone: String) {
        numberPhone = newNumberPhone
    }

    fun onAddressChange(newAddress: String) {
        address = newAddress
    }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            error = null

            try {
                val customer = Customer(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    email = email,
                    password = password,
                    numberPhone = numberPhone,
                    address = address,
                    createdAt = System.currentTimeMillis().toString(),
                    updatedAt = System.currentTimeMillis().toString()
                )

                repository.register(customer)
                    .onSuccess {
                        this@SignupViewModel.customer = it
                        onSuccess()
                    }
                    .onFailure {
                        error = it.message
                    }
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }
} 