package com.safronov.apex.example.interesting_fact_about_number.domain.use_case

import com.safronov.apex.example.interesting_fact_about_number.domain.model.fact.output.FactAboutNumber
import com.safronov.apex.example.interesting_fact_about_number.domain.repository.NumberRepository
import com.safronov.apex.example.interesting_fact_about_number.domain.response.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCachedFactsUseCase @Inject constructor(
    private val numberRepository: NumberRepository
) {
    operator fun invoke(): Flow<Response<List<FactAboutNumber>>> = numberRepository.getCachedFacts()
}