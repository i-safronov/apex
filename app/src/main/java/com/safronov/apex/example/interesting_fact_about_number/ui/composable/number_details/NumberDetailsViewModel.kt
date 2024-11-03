package com.safronov.apex.example.interesting_fact_about_number.ui.composable.number_details

import com.safronov.apex.apex.EffectorScope
import com.safronov.apex.apex.ExecutorScope
import com.safronov.apex.apex.APEXViewModel
import com.safronov.apex.example.interesting_fact_about_number.ui.composable.number_details.NumberDetailsContract.Effect
import com.safronov.apex.example.interesting_fact_about_number.ui.composable.number_details.NumberDetailsContract.Event
import com.safronov.apex.example.interesting_fact_about_number.ui.composable.number_details.NumberDetailsContract.Executor
import com.safronov.apex.example.interesting_fact_about_number.ui.composable.number_details.NumberDetailsContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NumberDetailsViewModel @Inject constructor() : APEXViewModel<State, Executor, Effect, Event>(
    initState = State()
) {

    override suspend fun ExecutorScope<Effect, Event>.execute(ex: Executor): State = when (ex) {
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