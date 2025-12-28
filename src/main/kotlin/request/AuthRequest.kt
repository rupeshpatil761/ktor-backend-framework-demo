package com.rupesh.request

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val username:String,
    val password:String
)