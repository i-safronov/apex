package com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.numbers_list

import com.safronov.apex.udf.UDF
import com.safronov.apex_udf.example.interesting_fact_about_number.domain.model.fact.output.FactAboutNumber

object NumbersListContract {

    data class State(
        val isLoading: Boolean = false,
        val isObtainingFact: Boolean = false,
        val error: String? = null,
        val inputError: String? = null
    ): UDF.State

    sealed interface Executor: UDF.Executor {
        data object Init: Executor
        data class GetFactByNumber(val str: String): Executor
        data object GetFactByRandomNumber: Executor
        data class AddFact(val factAboutNumber: FactAboutNumber): Executor
    }

}