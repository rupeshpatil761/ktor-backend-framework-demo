package com.rupesh.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.bearer

fun Application.configureBearerAuth() {

    val userDb : Map<String, String> = mapOf(
        "token1" to "user1",
        "token2" to "user2"
    )

    install(Authentication) {
        bearer("bearer-auth") {
            realm = "Access protected routes"
            authenticate { tokenCreds ->
                val user = userDb[tokenCreds.token]
                if(!user.isNullOrBlank()) {
                    UserIdPrincipal(user)
                } else {
                    null
                }
            }
        }
    }
}