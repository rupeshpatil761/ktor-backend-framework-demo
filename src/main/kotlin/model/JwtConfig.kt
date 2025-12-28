package com.rupesh.model

data class JwtConfig(
    val secret: String,
    val realm: String,
    val issuer: String,
    val audience: String,
    val tokenExpiry: Long
)