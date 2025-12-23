package com.rupesh.plugins

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.request.receiveNullable
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.RoutingRoot
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable

fun Application.configureRouting() {

    install(RoutingRoot) {

        route("/", HttpMethod.Get) {
            handle {
                call.respondText { "Hello World Override" }
            }
        }
    }

    routing {
        // when paths are same first route gets higher priority
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

        typeSafeRoutes()

        nestedRoutes();

        handlingJsonObject()

    }
}

private fun Route.handlingJsonObject() {

    post("product") {
        val product = call.receiveNullable<Product>() ?:
            return@post call.respond(HttpStatusCode.BadRequest)
        call.respond(product)
    }
}

@Serializable
data class Product (
    val name: String,
    val category: String,
    val price: Int
)

// Example
// GET /accounts/users
// GET /accounts/users/{id}
fun Route.nestedRoutes() {
    route("accounts") {
        route("users") {
            get() { call.respondText { "Return all users" }}
            get("{id}") {
                val id = call.pathParameters["id"]
                call.respondText { "Return user with id: $id" }
            }
        }
    }
}

fun Route.typeSafeRoutes() {
    // this get function is from resource package
    get<Blogs> {blogs ->
        val sort = blogs.sort
        call.respondText ( "Sort order is $sort")
    }

    delete<Blogs.Blog> { blog ->
        val id = blog.id;
        val sort = blog.parent.sort
        call.respondText { "Delete request for blog id: $id and sort order is : $sort" }
    }
}

// Type safe Routing
// example:
// GET /blogs/{id}?sort=new
// DELETE /blogs/213?sort=old

@Resource("blogs")
class Blogs(val sort: String? = "new") {

    @Resource("{id}")
    data class Blog(val parent: Blogs = Blogs(), val id: String)
}