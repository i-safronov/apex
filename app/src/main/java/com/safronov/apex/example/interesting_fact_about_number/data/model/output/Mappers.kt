package com.safronov.apex.example.interesting_fact_about_number.data.model.output

import com.safronov.apex.example.interesting_fact_about_number.domain.model.fact.output.FactAboutNumber

fun FactAboutNumberEntity.mapToDomain() = FactAboutNumber(
    id = id ?: -1,
    number = number ?: -1,
    fact = fact ?: ""
)

fun FactAboutNumberDTO.mapToEntity(inputNumber: Long, fact: String) = FactAboutNumberEntity(
    fact = fact,
    number = inputNumber
)