package com.rupesh.routes

import com.rupesh.model.Product
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.handlingJsonObject() {
    post("json-test") {
        val product = call.receiveNullable<Product>() ?:
        return@post call.respond(HttpStatusCode.BadRequest)
        call.respond(product)
    }
}

