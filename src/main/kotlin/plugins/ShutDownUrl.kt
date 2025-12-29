package com.rupesh.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.ShutDownUrl

fun Application.configureShutDownURL(){

    // Approach 1: Configure it in application.yml

    // Approach 2
    install(ShutDownUrl.ApplicationCallPlugin){
        shutDownUrl = "/admin/shutdown"
        exitCodeSupplier = { 0 }
    }

}