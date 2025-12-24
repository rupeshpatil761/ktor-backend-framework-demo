package com.rupesh.responses

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val errors: List<Error>
) {
    @Serializable
    data class Error(
        val status: String,
        val message: String
    )
}