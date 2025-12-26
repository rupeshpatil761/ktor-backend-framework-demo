package com.rupesh.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.http.content.LocalPathContent
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.server.response.respondRedirect
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import java.io.File
import java.nio.file.Path
import kotlin.io.path.exists

fun Route.mediaRoutes() {

    get("/image") {
       val filePath = call.request.queryParameters["filePath"] ?: ""
        val file = File(filePath)
        if(!file.exists()) return@get call.respond(HttpStatusCode.NotFound, "File not found at path: $filePath")
        call.respondFile(file)
    }

    get("/fileFromPath") {
        val fileName = call.request.queryParameters["filePath"] ?: ""
        val filePath = Path.of(fileName)
        if(!filePath.exists()) return@get call.respond(HttpStatusCode.NotFound, "File not found at path: $filePath")
        call.respond(LocalPathContent(filePath))
    }

    get("/headers") {
        // option 1 -  repeat below for multiple headers
        call.response.header(
            "Custom-Header",
            "Custom Header Value"
        )
        // option 2
        call.response.headers.append("X-Multiple", "Value1")
        call.response.headers.append("X-Multiple", "Value2")
        call.respond(HttpStatusCode.OK)
    }

    get("/cookies") {
        call.response.cookies.append(
            "new-cookie", "new cookie value"
        )
        call.respond(HttpStatusCode.OK)
    }

    get("/redirect") {
        call.respondRedirect("/moved")
    }

    get("/moved") {
        call.respondText("Redirected to moved route")
    }
}