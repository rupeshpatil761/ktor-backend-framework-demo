package com.rupesh

import com.rupesh.model.JwtConfig
import com.rupesh.plugins.configureBasicAuthentication
import com.rupesh.plugins.configureBearerAuth
import com.rupesh.plugins.configureJwtAuth
import com.rupesh.plugins.configureRateLimit
import com.rupesh.plugins.configureRequestValidation
import com.rupesh.plugins.configureResources
import com.rupesh.plugins.configureRouting
import com.rupesh.plugins.configureSerialization
import com.rupesh.plugins.configureShutDownURL
import com.rupesh.plugins.configureStatusPages
import com.rupesh.plugins.configureWebsockets
import io.ktor.server.application.*
import io.ktor.server.config.ApplicationConfig

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val jwtConfig = getJwtConfig(environment.config.config("ktor.jwt"))

   // resources and rate-limit should be configured before routing
    configureResources()
    configureRateLimit()
    // Note: At a time only one authentication plugin can be installed
    //configureBasicAuthentication()
    //configureBearerAuth()
    configureJwtAuth(jwtConfig)
    configureWebsockets()
    configureRouting(jwtConfig)
    configureSerialization()
    configureStatusPages()
    configureRequestValidation()
    configureShutDownURL()
}


private fun getJwtConfig(jwt: ApplicationConfig) : JwtConfig {

    val realm = jwt.property("realm").getString()
    val secret = jwt.property("secret").getString()
    val issuer = jwt.property("issuer").getString()
    val audience = jwt.property("audience").getString()
    val expiry = jwt.property("expiry").getString().toLong()

    return JwtConfig(
        realm = realm,
        issuer = issuer,
        secret = secret,
        audience = audience,
        tokenExpiry = expiry
    )
}