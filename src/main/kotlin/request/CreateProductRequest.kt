package com.rupesh.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateProductRequest(
    val data: Data? = null
) {
    @Serializable
    data class Data(
        val type: String? = null,
        val attributes: Attributes? = null
    )

    @Serializable
    data class Attributes(
        val name: String? = null,
        val category: String? = null,
        val price: Int? = null
    )
}