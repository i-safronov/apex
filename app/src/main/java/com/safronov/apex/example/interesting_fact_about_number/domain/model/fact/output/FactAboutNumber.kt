package com.safronov.apex.example.interesting_fact_about_number.domain.model.fact.output

data class FactAboutNumber(
    val id: Int,
    val number: Long,
    val fact: String
)
