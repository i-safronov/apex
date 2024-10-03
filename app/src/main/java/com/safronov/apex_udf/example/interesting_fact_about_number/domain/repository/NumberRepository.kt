package com.safronov.apex_udf.example.interesting_fact_about_number.domain.repository

import com.safronov.apex_udf.example.interesting_fact_about_number.domain.model.fact.input.GetFactAboutNumberInput
import com.safronov.apex_udf.example.interesting_fact_about_number.domain.model.fact.output.FactAboutNumber
import com.safronov.apex_udf.example.interesting_fact_about_number.domain.response.Response
import kotlinx.coroutines.flow.Flow

interface NumberRepository {

    fun getFactAboutNumber(getFactAboutNumberInput: GetFactAboutNumberInput): Flow<Response<FactAboutNumber>>
    fun getFactAboutRandomNumber(): Flow<Response<FactAboutNumber>>
    fun getCachedFacts(): Flow<Response<List<FactAboutNumber>>>

}