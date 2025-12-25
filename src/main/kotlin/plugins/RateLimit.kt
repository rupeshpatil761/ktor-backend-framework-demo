package com.rupesh.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.ratelimit.RateLimit
import io.ktor.server.plugins.ratelimit.RateLimitName
import kotlin.time.Duration.Companion.seconds

fun Application.configureRateLimit() {
    install(RateLimit) {

        /*global {
            // by default, every 60 seconds, 5 api calls are allowed
            rateLimiter(limit = 5, refillPeriod = 60.seconds)
        }*/

        // if you want to use this rate limiter, need to disable the global config
        register(RateLimitName("getProductLimit")) {
            rateLimiter(limit = 10, refillPeriod = 60.seconds)
        }

        register(RateLimitName("dynamicQueryParamLimit")) {
            rateLimiter(limit = 10, refillPeriod = 60.seconds)
            requestKey { call ->
                call.request.queryParameters["type"] ?: ""
            }
            // when product type set to Electric in request, rate limit weight i.e. will be used.
            // i.e. 10/2 = 5 --> only 5 requests will be allowed in 1 min
            // For all other types, every 60 seconds, 10 api calls are allowed
            requestWeight { call, key ->
                when (key) {
                    "Electric" -> 2
                    else -> 1
                }
            }
        }
    }
}