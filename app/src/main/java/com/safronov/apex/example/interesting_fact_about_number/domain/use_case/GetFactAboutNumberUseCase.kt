package com.safronov.apex.example.interesting_fact_about_number.domain.use_case

import com.safronov.apex.example.interesting_fact_about_number.domain.model.fact.input.GetFactAboutNumberInput
import com.safronov.apex.example.interesting_fact_about_number.domain.repository.NumberRepository
import javax.inject.Inject

class GetFactAboutNumberUseCase @Inject constructor(
    private val numberRepository: NumberRepository
) {
    operator fun invoke(getFactAboutNumberInput: GetFactAboutNumberInput) =
        numberRepository.getFactAboutNumber(getFactAboutNumberInput = getFactAboutNumberInput)
}