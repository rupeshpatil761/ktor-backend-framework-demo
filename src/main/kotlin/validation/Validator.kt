package com.rupesh.validation

import io.ktor.server.plugins.requestvalidation.ValidationResult

internal interface Validator<T> {
    fun validate(request: T) : ValidationResult
}

fun combineValidationResults(vararg results: ValidationResult) : ValidationResult {
    val invalidReasons = results
        .filterIsInstance<ValidationResult.Invalid>()
        .flatMap { it.reasons }
    return if (invalidReasons.isEmpty()) ValidationResult.Valid else ValidationResult.Invalid(invalidReasons)
}