package com.bobrito.auth.ui.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobrito.auth.data.model.Customer
import com.bobrito.auth.data.model.LoginRequest
import com.bobrito.auth.data.repository.AuthRepository
import kotlinx.coroutines.launch

class SigninViewModel : ViewModel() {
    private val repository = AuthRepository()

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var customer by mutableStateOf<Customer?>(null)

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val loginRequest = LoginRequest(email = email, password = password)
                repository.login(loginRequest)
                    .onSuccess {
                        customer = it
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