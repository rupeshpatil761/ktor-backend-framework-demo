package com.rupesh.routes

import com.rupesh.model.JwtConfig
import com.rupesh.plugins.generateToken
import com.rupesh.request.AuthRequest
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.configureUserMgmtRoutes(config: JwtConfig) {

    val usersDB = mutableMapOf<String,String>()

    post("/signup"){
        val requestData = call.receive<AuthRequest>()
        if (usersDB.containsKey(requestData.username)){
            call.respondText("User already exists")
        }else{
            usersDB[requestData.username] = requestData.password
            val jwtToken = generateToken(config = config, userName = requestData.username)
            call.respond(mapOf(
                "message" to "User signup success",
                "token" to jwtToken
            ))
        }
    }


    post("/login"){
        val requestData = call.receive<AuthRequest>()

        val storedPassword = usersDB[requestData.username]
            ?: return@post call.respondText("User doesn't exists")

        if (storedPassword == requestData.password){
            val jwtToken = generateToken(config = config, userName = requestData.username)
            call.respond(mapOf(
                "message" to "Login success",
                "token" to jwtToken
            ))
        }else{
            call.respondText("Invalid credentials")
        }
    }
}
