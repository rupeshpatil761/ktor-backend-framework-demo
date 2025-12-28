package com.rupesh.routes

import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.authenticatedRoutes() {

    // basic auth
    /*authenticate("basic-auth") {
        get("/test-basic-auth-1") {
            call.respondText { "Hello There!!" }
        }
    }*/

    // bearer auth
    /*authenticate("bearer-auth") {
        get("/test-bearer-auth-1") {
            val userName = call.principal<UserIdPrincipal>()?.name
            call.respondText { "Hello $userName!!" }
        }
    }*/

    // jwt auth
    authenticate("jwt-auth") {
        get("/test-jwt"){
            val principal = call.principal<JWTPrincipal>()
            val username = principal?.payload?.getClaim("username")?.asString()
            val expiresAt = principal?.expiresAt?.time?.minus(System.currentTimeMillis())
            call.respondText("Hello, $username! The token expires after $expiresAt ms.")
        }
    }
}