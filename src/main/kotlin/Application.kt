package com.rupesh

import com.rupesh.plugins.configureBasicAuthentication
import com.rupesh.plugins.configureBearerAuth
import com.rupesh.plugins.configureRateLimit
import com.rupesh.plugins.configureRequestValidation
import com.rupesh.plugins.configureResources
import com.rupesh.plugins.configureRouting
import com.rupesh.plugins.configureSerialization
import com.rupesh.plugins.configureStatusPages
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
   // resources and rate-limit should be configured before routing
    configureResources()
    configureRateLimit()
    // Note: At a time only one authentication plugin can be installed
    //configureBasicAuthentication()
    configureBearerAuth()
    configureRouting()
    configureSerialization()
    configureStatusPages()
    configureRequestValidation()
}
