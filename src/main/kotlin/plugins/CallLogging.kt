package com.rupesh.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.request.*
import org.slf4j.event.Level

fun Application.configureCallLogging(){
    install(CallLogging){
        level = Level.INFO

        //filter { call -> call.request.path().startsWith("/hi") }

        format {call ->
            val correlationId = call.request.headers["X-Correlation-Id"] ?: "Unknown-Correlation-Id"
            "CorrelationId: $correlationId, Method: ${call.request.httpMethod.value}, Path: ${call.request.path()}"
        }
    }
}