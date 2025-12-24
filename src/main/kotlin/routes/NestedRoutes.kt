package com.rupesh.routes

import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

// Example
// GET /accounts/users
// GET /accounts/users/{id}
fun Route.nestedRoutes() {
    route("accounts") {
        route("users") {
            get() { call.respondText { "Return all users" }}
            get("{id}") {
                val id = call.pathParameters["id"]
                call.respondText { "Return user with id: $id" }
            }
        }
    }
}