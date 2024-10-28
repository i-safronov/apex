package com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.number_details

import com.safronov.apex.udf.EffectorScope
import com.safronov.apex.udf.ExecutorScope
import com.safronov.apex.udf.UDFViewModel
import com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.number_details.NumberDetailsContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NumberDetailsViewModel @Inject constructor() : UDFViewModel<State, Executor, Effect, Event>(
    initState = State()
) {

    override suspend fun ExecutorScope<Effect>.execute(ex: Executor): State = when (ex) {
        is Executor.SetProperties -> {
            state.copy(
                number = ex.number,
                description = ex.description
            )
        }
    }

    override suspend fun EffectorScope<Executor>.affect(ef: Effect) = when (ef) {
        else -> {}
    }

}