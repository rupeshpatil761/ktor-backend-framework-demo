package com.rupesh.validation

import com.rupesh.request.CreateProductRequest
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateCreateProductRequest() {
    validate<CreateProductRequest>(CreateProductRequestValidator::validate)
}

object CreateProductRequestValidator : Validator<CreateProductRequest> {
    override fun validate(request: CreateProductRequest): ValidationResult {
        return dataValidations(request.data)
    }

    private val dataValidations : (CreateProductRequest.Data?) -> ValidationResult =
        { data ->
            if(data == null) {
                ValidationResult.Invalid("data cannot be null")
            } else {
                combineValidationResults(
                    if (data.type == null) ValidationResult.Invalid("Data type cannot be null") else ValidationResult.Valid,
                    if (data.attributes == null) ValidationResult.Invalid("Data Attributes cannot be null") else ValidationResult.Valid
                )
            }
        }
}