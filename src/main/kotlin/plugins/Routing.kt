package com.rupesh.plugins

import com.rupesh.model.JwtConfig
import com.rupesh.routes.authenticatedRoutes
import com.rupesh.routes.configureUserMgmtRoutes
import com.rupesh.routes.handlingJsonObject
import com.rupesh.routes.mediaRoutes
import com.rupesh.routes.nestedRoutes
import com.rupesh.routes.pathAndQueryParmsRoutes
import com.rupesh.routes.requestValidationExample
import com.rupesh.routes.typeSafeRoutes
import com.rupesh.routes.websocketRoutes
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.response.respondText
import io.ktor.server.routing.RoutingRoot
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configureRouting(jwtConfig: JwtConfig) {

    // explicit plugin installation. Avoid using this approach
    install(RoutingRoot) {

        route("/", HttpMethod.Get) {
            handle {
                call.respondText { "Hello World Override" }
            }
        }
    }

    // routing is convenient extension function
    routing {
        // when paths are same first route gets higher priority
        // this get function is from routing package
        get("/") {
            call.respondText("Hello World!")
        }

        pathAndQueryParmsRoutes()

        typeSafeRoutes()

        nestedRoutes();

        handlingJsonObject()

        requestValidationExample()

        mediaRoutes()

        authenticatedRoutes()

        configureUserMgmtRoutes(jwtConfig)

        websocketRoutes()

    }
}