package com.safronov.apex_udf.example.interesting_fact_about_number.data.repository

import com.safronov.apex_udf.example.interesting_fact_about_number.data.db.FactAboutNumberDao
import com.safronov.apex_udf.example.interesting_fact_about_number.data.service.FactAboutNumberService
import com.safronov.apex_udf.example.interesting_fact_about_number.domain.model.fact.input.GetFactAboutNumberInput
import com.safronov.apex_udf.example.interesting_fact_about_number.domain.model.fact.output.FactAboutNumber
import com.safronov.apex_udf.example.interesting_fact_about_number.domain.repository.NumberRepository
import com.safronov.apex_udf.example.interesting_fact_about_number.domain.response.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NumberRepositoryImpl @Inject constructor(
    private val factAboutNumberDao: FactAboutNumberDao,
    private val factAboutNumberService: FactAboutNumberService
): NumberRepository {

    override fun getFactAboutNumber(getFactAboutNumberInput: GetFactAboutNumberInput): Flow<Response<FactAboutNumber>> {
        TODO("Not yet implemented")
    }

    override fun getFactAboutRandomNumber(): Flow<Response<FactAboutNumber>> {
        TODO("Not yet implemented")
    }

    override fun getCachedFacts(): Flow<Response<List<FactAboutNumber>>> {
        TODO("Not yet implemented")
    }

}