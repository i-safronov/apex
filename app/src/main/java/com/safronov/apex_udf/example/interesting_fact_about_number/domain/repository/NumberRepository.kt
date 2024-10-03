package com.safronov.apex_udf.example.interesting_fact_about_number.domain.repository

import com.safronov.apex_udf.example.interesting_fact_about_number.domain.model.fact.input.GetFactAboutNumberInput
import com.safronov.apex_udf.example.interesting_fact_about_number.domain.model.fact.output.FactAboutNumber
import com.safronov.apex_udf.example.interesting_fact_about_number.domain.response.Response
import kotlinx.coroutines.flow.Flow

interface NumberRepository {

    fun getFactAboutNumber(getFactAboutNumberInput: GetFactAboutNumberInput): Flow<Response<Unit>>
    fun getFactAboutRandomNumber(): Flow<Response<Unit>>
    fun getCachedFacts(): Flow<Response<List<FactAboutNumber>>>

}