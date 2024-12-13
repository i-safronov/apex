package com.safronov.apex.example.interesting_fact_about_number.domain.validator

import com.safronov.apex.example.interesting_fact_about_number.domain.response.Response

class StringToLongValidator {

    fun validate(input: String): Response<Long> {
        return when {
            input.isBlank() -> {
                Response.Error(error = Exception("Input text cannot be empty"))
            }
            input.first() == '0' -> {
                Response.Error(error = Exception("Enter a valid number"))
            }
            input.all { it.isDigit() } -> {
                Response.Success(data = input.toLong())
            }
            else -> {
                Response.Error(error = Exception("Input must contain only digits"))
            }
        }
    }

}