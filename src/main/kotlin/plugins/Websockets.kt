package com.rupesh.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun Application.configureWebsockets() {
    install(WebSockets) {
        // Ktor will send a Ping frame every 10 seconds
        pingPeriod = 10.seconds

        // If the client doesn't respond with a Pong within 5 seconds,
        // the connection is closed.
        timeout = 5.seconds

        // Maximum frame size (optional but good for security)
        maxFrameSize = Long.MAX_VALUE

        masking = false
    }
}