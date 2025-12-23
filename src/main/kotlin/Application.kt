package com.rupesh

import com.rupesh.plugins.configureResources
import com.rupesh.plugins.configureRouting
import com.rupesh.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
   // resources should be configured before routing
    configureResources()
    configureRouting()
    configureSerialization()
}
