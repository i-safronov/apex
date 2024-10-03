package com.safronov.apex_udf.example.interesting_fact_about_number.data.repository

import com.safronov.apex_udf.example.interesting_fact_about_number.data.db.FactAboutNumberDao
import com.safronov.apex_udf.example.interesting_fact_about_number.data.model.output.FactAboutNumberDTO
import com.safronov.apex_udf.example.interesting_fact_about_number.data.model.output.mapToEntity
import com.safronov.apex_udf.example.interesting_fact_about_number.data.response.onlySuccess
import com.safronov.apex_udf.example.interesting_fact_about_number.data.service.FactAboutNumberService
import com.safronov.apex_udf.example.interesting_fact_about_number.domain.model.fact.input.GetFactAboutNumberInput
import com.safronov.apex_udf.example.interesting_fact_about_number.domain.model.fact.output.FactAboutNumber
import com.safronov.apex_udf.example.interesting_fact_about_number.domain.repository.NumberRepository
import com.safronov.apex_udf.example.interesting_fact_about_number.domain.response.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NumberRepositoryImpl @Inject constructor(
    private val factAboutNumberService: FactAboutNumberService,
    private val factAboutNumberDao: FactAboutNumberDao,
) : NumberRepository {

    override fun getFactAboutNumber(getFactAboutNumberInput: GetFactAboutNumberInput): Flow<Response<Unit>> {
        return flow {
            factAboutNumberService.getFactAboutNumber(number = getFactAboutNumberInput.number)
                .onlySuccess(
                    block = { it: FactAboutNumberDTO ->
                        extractFactAboutNumber(it, getFactAboutNumberInput)
                    },
                    scope = this@flow
                )
        }
    }

    override fun getFactAboutRandomNumber(): Flow<Response<Unit>> {
        return flow {
            factAboutNumberService.getFactAboutRandomNumber()
                .onlySuccess(
                    block = { it: FactAboutNumberDTO ->
                        extractFactAboutNumber(
                            it = it, getFactAboutNumberInput = GetFactAboutNumberInput(
                                number = it.number
                            )
                        )
                    },
                    scope = this@flow
                )
        }
    }

    override fun getCachedFacts(): Flow<Response<List<FactAboutNumber>>> {
        TODO("Not yet implemented")
    }

    private suspend fun FlowCollector<Response<Unit>>.extractFactAboutNumber(
        it: FactAboutNumberDTO,
        getFactAboutNumberInput: GetFactAboutNumberInput
    ) {
        factAboutNumberDao.insertFactAboutNumber(
            fact = it.mapToEntity(inputNumber = getFactAboutNumberInput.number)
        )
        emit(
            Response.Success(
                data = Unit
            )
        )
    }

}