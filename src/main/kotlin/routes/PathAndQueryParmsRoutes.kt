package com.rupesh.routes

import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.pathAndQueryParmsRoutes() {
    // path and query params route example
    get("/v1/blogs/{id}") {
        val id = call.pathParameters["id"]
        val queryParam1 = call.queryParameters["q1"]
        call.respondText { "Blog with id: $id & query param q1: $queryParam1" }
    }
}