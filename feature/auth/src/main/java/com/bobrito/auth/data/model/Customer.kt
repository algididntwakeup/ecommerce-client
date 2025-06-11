package com.bobrito.auth.data.model

data class Customer(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val numberPhone: String = "",
    val password: String = "",
    val address: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)

data class CustomerResponse(
    val success: Boolean,
    val data: Customer?,
    val message: String,
    val error: String?
)

data class CustomerListResponse(
    val success: Boolean,
    val data: List<Customer>,
    val message: String,
    val error: String?
)