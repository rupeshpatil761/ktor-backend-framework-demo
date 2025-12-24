package com.rupesh.routes

import com.rupesh.request.CreateProductRequest
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.requestValidationExample() {

    post("/product") {
        val request = call.receive<CreateProductRequest>()
        call.respondText { "Product Created Successfully" }

    }
}