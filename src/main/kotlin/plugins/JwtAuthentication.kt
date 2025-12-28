package com.rupesh.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.rupesh.model.JwtConfig
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respondText
import java.util.Date

fun Application.configureJwtAuth(config: JwtConfig) {
    install(Authentication) {
        jwt("jwt-auth") {
            realm = config.realm

            val jwtVerifier = JWT
                .require(Algorithm.HMAC256(config.secret))
                .withAudience(config.audience)
                .withIssuer(config.issuer)
                .build()

            verifier(jwtVerifier)

            validate { jwtCred ->
                // While creating token, we will be adding username as part of payload of jwt token
                val username = jwtCred.payload.getClaim("username").asString()
                if(!username.isNullOrBlank()) {
                    JWTPrincipal(jwtCred.payload)
                } else {
                    null
                }
            }

            challenge { scheme, realm ->
                call.respondText(text = "Token is not valid or expired",
                    status = HttpStatusCode.Unauthorized)
            }
        }
    }
}

fun generateToken(config: JwtConfig, userName: String) : String {
    return JWT.create()
        .withAudience(config.audience)
        .withIssuer(config.issuer)
        .withClaim("username", userName)
        .withExpiresAt(Date(System.currentTimeMillis() + config.tokenExpiry))
        .sign(Algorithm.HMAC256(config.secret))
}