package com.safronov.apex.example.interesting_fact_about_number.data.repository

import com.safronov.apex.example.interesting_fact_about_number.data.db.FactAboutNumberDao
import com.safronov.apex.example.interesting_fact_about_number.data.model.output.FactAboutNumberDTO
import com.safronov.apex.example.interesting_fact_about_number.data.model.output.mapToDomain
import com.safronov.apex.example.interesting_fact_about_number.data.model.output.mapToEntity
import com.safronov.apex.example.interesting_fact_about_number.data.response.onlySuccess
import com.safronov.apex.example.interesting_fact_about_number.data.service.FactAboutNumberService
import com.safronov.apex.example.interesting_fact_about_number.domain.model.fact.input.GetFactAboutNumberInput
import com.safronov.apex.example.interesting_fact_about_number.domain.model.fact.output.FactAboutNumber
import com.safronov.apex.example.interesting_fact_about_number.domain.repository.NumberRepository
import com.safronov.apex.example.interesting_fact_about_number.domain.response.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NumberRepositoryImpl @Inject constructor(
    private val factAboutNumberService: FactAboutNumberService,
    private val factAboutNumberDao: FactAboutNumberDao,
) : NumberRepository {

    override fun getFactAboutNumber(getFactAboutNumberInput: GetFactAboutNumberInput): Flow<Response<Unit>> {
        return flow<Response<Unit>> {
            factAboutNumberService.getFactAboutNumber(number = getFactAboutNumberInput.number)
                .onlySuccess(
                    block = { it: FactAboutNumberDTO ->
                        extractFactAboutNumber(
                            it = it,
                            getFactAboutNumberInput = getFactAboutNumberInput,
                            fact = it.text
                        )
                    },
                    scope = this@flow
                )
        }.catch { emit(Response.Error(error = it)) }
    }

    override fun getFactAboutRandomNumber(): Flow<Response<Unit>> {
        return flow<Response<Unit>> {
            factAboutNumberService.getFactAboutRandomNumber()
                .onlySuccess(
                    block = { it: FactAboutNumberDTO ->
                        extractFactAboutNumber(
                            it = it, getFactAboutNumberInput = GetFactAboutNumberInput(
                                number = it.number
                            ), fact = it.text
                        )
                    },
                    scope = this@flow
                )
        }.catch { emit(Response.Error(error = it)) }
    }

    override fun getCachedFacts(): Flow<Response<List<FactAboutNumber>>> {
        return factAboutNumberDao.getCachedFacts()
            .map { facts ->
                Response.Success(facts.map { it.mapToDomain() }) as Response<List<FactAboutNumber>>
            }
            .catch { emit(Response.Error(error = it)) }
    }

    private suspend fun FlowCollector<Response<Unit>>.extractFactAboutNumber(
        it: FactAboutNumberDTO,
        getFactAboutNumberInput: GetFactAboutNumberInput,
        fact: String
    ) {
        factAboutNumberDao.insertFactAboutNumber(
            fact = it.mapToEntity(inputNumber = getFactAboutNumberInput.number, fact = fact)
        )
        emit(
            Response.Success(
                data = Unit
            )
        )
    }

}