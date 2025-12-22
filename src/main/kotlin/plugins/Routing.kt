package com.rupesh.plugins

import io.ktor.http.HttpMethod
import io.ktor.resources.Resource
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.resources.get
import io.ktor.server.response.respondText
import io.ktor.server.routing.RoutingRoot
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing


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
        // this get function is from routing package
        get("/") {
            call.respondText("Hello World!")
        }

        // path and query params route example
        get("v1/blogs/{id}") {
            val id = call.pathParameters["id"]
            val queryParam1 = call.queryParameters["q1"]
            call.respondText { "Blog with id: $id & query param q1: $queryParam1" }
        }

        // this get function is from resource package
        get<Blogs> {blogs ->
            val sort = blogs.sort
            call.respondText ( "Sort order is $sort")
        }

    }
}

// Type safe Routing
// example: /blogs/{id}?sort=new

@Resource("blogs")
class Blogs(val sort: String? = "new") {

    @Resource("{id}")
    data class Blog(val parent: Blogs = Blogs(), val id: String)
}