package com.safronov.apex.example.interesting_fact_about_number.ui.composable.numbers_list

import com.safronov.apex.apex.APEX
import com.safronov.apex.example.interesting_fact_about_number.domain.model.fact.output.FactAboutNumber

object NumbersListContract {

    data class State(
        val isLoading: Boolean = false,
        val isObtainingFact: Boolean = false,
        val inputError: String? = null,
        val facts: List<FactAboutNumber> = emptyList(),
        val input: String = ""
    ): APEX.State

    sealed interface Executor: APEX.Executor {
        data object Init: Executor
        data object GetFactByNumber: Executor
        data object GetFactByRandomNumber: Executor
        data class ReplaceFacts(val factsAboutNumber: List<FactAboutNumber>): Executor
        data class Error(val throwable: Throwable): Executor
        data class OnFactClick(val factAboutNumber: FactAboutNumber): Executor
        data object ClearInput: Executor
        data class InputChanged(val input: String): Executor
    }

    sealed interface Event: APEX.Event {
        data class NavigateToFactDetails(val factAboutNumber: FactAboutNumber): Event
        data class ShowError(val error: String): Event
    }

    sealed interface Effect: APEX.Effect {
        data class GetFactByNumber(val number: Long): Effect
        data object GetFactByRandomNumber: Effect
        data object SubscribeOnNumbers: Effect
    }

}