package com.rupesh.routes

import io.ktor.server.auth.authenticate
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.authenticatedRoutes() {

    // basic auth
    authenticate("basic-auth") {
        get("/test-auth-1") {
            call.respondText { "Hello There!!" }
        }
    }

}