package com.rupesh.routes

import com.rupesh.request.CreateProductRequest
import io.ktor.server.plugins.origin
import io.ktor.server.plugins.ratelimit.RateLimitName
import io.ktor.server.plugins.ratelimit.rateLimit
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.requestValidationExample() {

    post("/product") {
        val request = call.receive<CreateProductRequest>()
        call.respondText { "Product Created Successfully" }
    }

    rateLimit(RateLimitName("getProductLimit")) {
        get("/product/{id}") {
            val param = call.pathParameters["id"]
            val requestLeft = call.response.headers["X-RateLimit-Remaining"]
            call.respondText { "Return product with id: $param and request left: $requestLeft "}
        }
    }

    rateLimit(RateLimitName("dynamicQueryParamLimit")) {
        get("/product") {
            val queryParam1 = call.queryParameters["type"]
            val requestLeft = call.response.headers["X-RateLimit-Remaining"]
            call.respondText { "Return product with type: $queryParam1 and request left: $requestLeft "}
        }
    }

    rateLimit(RateLimitName("per_ip")) {
        get("/products") {
            val ip = call.request.origin.remoteHost
            val requestLeft = call.response.headers["X-RateLimit-Remaining"]
            call.respondText { "Return all product for source IP: $ip  and request left: $requestLeft "}
        }
    }
}