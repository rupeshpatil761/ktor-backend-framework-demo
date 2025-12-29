package com.rupesh.routes

import io.ktor.server.routing.Route
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

fun Route.websocketRoutes() {

    val onlineUserSessions = ConcurrentHashMap<String, WebSocketSession>()

    val jsonConfig = Json { ignoreUnknownKeys = true }

    // How to access from postman?
    // ws://localhost:8080/chat?username=user1

    webSocket("chat") {
        val username = call.request.queryParameters["username"] ?: run {
            this.close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "" +
                    "username is required to establish the connection"))
            return@webSocket
        }

        // here this = DefaultWebSocketSession object
        onlineUserSessions[username] = this
        send("You are connected")

        try {
            for (frame in incoming) {
                when(frame) {
                    is Frame.Text -> {

                        val message = try {
                            jsonConfig.decodeFromString<Message>(frame.readText())
                        } catch (e: Exception) {
                            println("Unable to parse the message, ignore sending msg")
                            null
                        }
                        if (message != null) {
                            // when to is null, send message to all online users.
                            if (message.to.isNullOrBlank()) {
                                broadcastMessage(onlineUserSessions, username, message.text)
                            } else {
                                // Direct message to one user
                                onlineUserSessions[message.to]?.let { session ->
                                    launch {
                                        try {
                                            session.send("$username (private): ${message.text}")
                                        } catch (e: Exception) {
                                            // Handle failed send
                                            println("Failed to send message to user: $username")
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is Frame.Pong -> {
                        // Ktor handles Pongs automatically,
                        // but you can log it here to see the "heartbeat"
                        println("Received pong from $username")
                    }
                    else -> { /* Ignore other frame types */}
                }
            }
        } finally {
            // Remove the user's session
            onlineUserSessions.remove(username)
            // --- BROADCAST LEFT ---
            broadcastMessage(onlineUserSessions, "SERVER", "$username left the chat")
            // release resources
            this.close()
        }
    }
}

/**
 * Thread-safe broadcast helper
 * Uses 'launch' so one slow user doesn't block the whole loop
 */
private fun broadcastMessage(sessions: ConcurrentHashMap<String, WebSocketSession>, sender: String, text: String) {

    sessions.entries
        .filter { it.key != sender }
        .forEach { (username, session) ->
            session.launch {
                try {
                    session.send("$sender: $text")
                } catch (e: Exception) {
                    // If a send fails, the session is likely dead;
                    // the heartbeat/incoming loop will clean it up
                    println("Failed to send message to all users")
                }
            }
        }
}

@Serializable
data class Message(
    val text: String,
    val to: String? = null
)


// To Test Heartbeats in Postman -- Ask Gemini " How to test Ktor Websockets Heartbeats in Postman"