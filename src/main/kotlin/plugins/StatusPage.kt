package com.rupesh.plugins

import com.rupesh.responses.ErrorResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("ApplicationLogger")

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<RequestValidationException>(::validationBadRequestResponse)
        exception<Throwable>(::genericExceptionResponse)
    }
}

private suspend fun validationBadRequestResponse(call: ApplicationCall, cause: RequestValidationException) {
    logger.error("RequestValidationException occurred: ${cause.message}")
    val errorDetails = cause.reasons.joinToString().takeIf { it.isNotBlank() }
    call.respond(
        status = HttpStatusCode.BadRequest,
        message = ErrorResponse(
            errors = listOf(
                ErrorResponse.Error(
                    status= HttpStatusCode.BadRequest.value.toString(),
                    message = errorDetails ?: "The request could not be processed due to invalid input"
                )
            )
        ),
    )
}

private suspend fun genericExceptionResponse(
    call : ApplicationCall,
    cause: Throwable
) {
    logger.error("Internal Server Error occurred: ${cause.message}")
    call.respond(
        status = HttpStatusCode.InternalServerError,
        message = ErrorResponse(
            errors = listOf(
                ErrorResponse.Error(
                    status= HttpStatusCode.InternalServerError.value.toString(),
                    message = cause.message.toString()
                )
            )
        ),
    )
}
