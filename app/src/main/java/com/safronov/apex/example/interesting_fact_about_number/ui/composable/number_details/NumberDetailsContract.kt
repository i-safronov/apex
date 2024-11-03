package com.safronov.apex.example.interesting_fact_about_number.ui.composable.number_details

import com.safronov.apex.apex.APEX

class NumberDetailsContract {

    data class State(
        val number: String = "",
        val description: String = ""
    ): APEX.State

    sealed interface Executor: APEX.Executor {
        data class SetProperties(
            val number: String,
            val description: String
        ): Executor
    }

    sealed interface Effect: APEX.Effect

    sealed interface Event: APEX.Event

}