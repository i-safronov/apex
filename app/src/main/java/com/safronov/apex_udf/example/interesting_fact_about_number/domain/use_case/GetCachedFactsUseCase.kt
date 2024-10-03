package com.safronov.apex_udf.example.interesting_fact_about_number.domain.use_case

import com.safronov.apex_udf.example.interesting_fact_about_number.domain.repository.NumberRepository
import javax.inject.Inject

class GetCachedFactsUseCase @Inject constructor(
    private val numberRepository: NumberRepository
) {
    operator fun invoke() = numberRepository.getCachedFacts()
}