package com.bobrito.auth.ui.signin

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bobrito.auth.data.local.SessionManager
import com.bobrito.auth.data.model.Customer
import com.bobrito.auth.data.model.LoginRequest
import com.bobrito.auth.data.repository.AuthRepository
import kotlinx.coroutines.launch

class SigninViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AuthRepository()
    private val sessionManager = SessionManager(application)

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var customer by mutableStateOf<Customer?>(null)

    init {
        // Check if user is already logged in
        customer = sessionManager.getUser()
    }

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
                val loginRequest = LoginRequest(email, password)
                repository.login(loginRequest)
                    .onSuccess {
                        customer = it
                        sessionManager.saveUser(it)
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

    fun isLoggedIn(): Boolean = sessionManager.isLoggedIn()

    fun logout() {
        sessionManager.logout()
        customer = null
    }
}