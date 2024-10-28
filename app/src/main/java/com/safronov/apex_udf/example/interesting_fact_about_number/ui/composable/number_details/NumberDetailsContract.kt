package com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.number_details

import com.safronov.apex.udf.UDF

class NumberDetailsContract {

    data class State(
        val number: String = "",
        val description: String = ""
    ): UDF.State

    sealed interface Executor: UDF.Executor {
        data class SetProperties(
            val number: String,
            val description: String
        ): Executor
    }

}