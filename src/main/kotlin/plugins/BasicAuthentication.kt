package com.rupesh.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserHashedTableAuth
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.basic
import io.ktor.util.getDigestFunction

fun Application.configureBasicAuthentication() {

    val hashedUserTable = createHashedUserTable()

    install(Authentication) {
        basic("basic-auth") {
            validate { creds ->
                if(creds.name == "admin" &&
                    creds.password == "password"
                ) {
                    UserIdPrincipal(creds.name)
                } else {
                    null
                }

                // OR we can use Kotlin's in-memory database
                //hashedUserTable.authenticate(creds)
            }
        }
    }
}

fun createHashedUserTable () : UserHashedTableAuth{
    val digestFunction = getDigestFunction("SHA-256"){ "ktor${it.length}" }
    return UserHashedTableAuth(
        digester = digestFunction,
        table = mapOf(
            "admin" to digestFunction("password"),
            "user" to digestFunction("123")
        )
    )
}