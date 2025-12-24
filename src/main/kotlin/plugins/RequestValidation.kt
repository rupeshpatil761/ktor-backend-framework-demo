package com.rupesh.plugins

import com.rupesh.validation.validateCreateProductRequest
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation

fun Application.configureRequestValidation() {
    install(RequestValidation) {

        validateCreateProductRequest()

    }
}