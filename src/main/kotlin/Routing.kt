package com.rupesh

import io.ktor.http.HttpMethod
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    install(RoutingRoot) {

        route("/", HttpMethod.Get) {
            handle {
                call.respondText { "Hello World Override" }
            }
        }
    }

    // when paths are same first route gets higher priority
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        // path and query params route example
        get("v1/blogs/{id}") {
            val id = call.pathParameters["id"]
            val queryParam1 = call.queryParameters["q1"]
            call.respondText { "Blog with id: $id & query param q1: $queryParam1" }
        }

    }
}
