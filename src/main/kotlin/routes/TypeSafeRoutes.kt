package com.rupesh.routes

import io.ktor.resources.Resource
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route

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