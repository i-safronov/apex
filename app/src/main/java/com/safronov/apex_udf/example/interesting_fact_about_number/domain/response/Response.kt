package com.safronov.apex_udf.example.interesting_fact_about_number.domain.response

sealed class Response<T> {
    data class Success<T>(val data: T): Response<T>()
    data class Error<T>(val error: Throwable): Response<T>()
}