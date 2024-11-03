package com.safronov.apex.example.interesting_fact_about_number.ui.composable.numbers_list

import com.safronov.apex.apex.EffectorScope
import com.safronov.apex.apex.ExecutorScope
import com.safronov.apex.apex.APEXViewModel
import com.safronov.apex.example.interesting_fact_about_number.domain.model.fact.input.GetFactAboutNumberInput
import com.safronov.apex.example.interesting_fact_about_number.domain.response.Response
import com.safronov.apex.example.interesting_fact_about_number.domain.use_case.GetCachedFactsUseCase
import com.safronov.apex.example.interesting_fact_about_number.domain.use_case.GetFactAboutNumberUseCase
import com.safronov.apex.example.interesting_fact_about_number.domain.use_case.GetFactAboutRandomNumberUseCase
import com.safronov.apex.example.interesting_fact_about_number.domain.validator.StringToLongValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.safronov.apex.example.interesting_fact_about_number.ui.composable.numbers_list.NumbersListContract.State
import com.safronov.apex.example.interesting_fact_about_number.ui.composable.numbers_list.NumbersListContract.Effect
import com.safronov.apex.example.interesting_fact_about_number.ui.composable.numbers_list.NumbersListContract.Event
import com.safronov.apex.example.interesting_fact_about_number.ui.composable.numbers_list.NumbersListContract.Executor

@HiltViewModel
class NumbersListViewModel @Inject constructor(
    private val getFactAboutNumberUseCase: GetFactAboutNumberUseCase,
    private val getFactAboutRandomNumberUseCase: GetFactAboutRandomNumberUseCase,
    private val getCachedFactsUseCase: GetCachedFactsUseCase,
) : APEXViewModel<State, Executor, Effect, Event>(State()) {

    private val stringToLongValidator = StringToLongValidator()

    init {
        dispatch(Executor.Init)
    }

    override suspend fun ExecutorScope<Effect, Event>.execute(ex: Executor): State = when (ex) {
        Executor.Init -> {
            sendEffect(Effect.SubscribeOnNumbers)
            state.copy(
                isLoading = true
            )
        }

        is Executor.ReplaceFacts -> {
            state.copy(
                isLoading = false,
                isObtainingFact = false,
                facts = ex.factsAboutNumber
            )
        }

        is Executor.GetFactByNumber -> {
            when (val validate = stringToLongValidator.validate(input = state.input.trim())) {
                is Response.Success -> {
                    sendEffect(Effect.GetFactByNumber(number = validate.data))
                    state.copy(
                        isObtainingFact = true
                    )
                }

                is Response.Error -> {
                    state.copy(
                        inputError = validate.error.message
                    )
                }
            }
        }

        Executor.GetFactByRandomNumber -> {
            sendEffect(Effect.GetFactByRandomNumber)
            state.copy(
                isObtainingFact = true
            )
        }

        is Executor.Error -> {
            sendEvent(Event.ShowError(
                error = ex.throwable.message ?: "Unknown error"
            ))
            state.copy(
                isLoading = false,
                isObtainingFact = false
            )
        }

        is Executor.OnFactClick -> {
            sendEvent(Event.NavigateToFactDetails(factAboutNumber = ex.factAboutNumber))
            state
        }

        is Executor.InputChanged -> {
            state.copy(
                input = ex.input,
                inputError = null
            )
        }

        Executor.ClearInput -> {
            state.copy(
                input = ""
            )
        }
    }

    override suspend fun EffectorScope<Executor>.affect(ef: Effect) = when (ef) {
        is Effect.GetFactByNumber -> {
            getFactAboutNumberUseCase(
                getFactAboutNumberInput = GetFactAboutNumberInput(number = ef.number)
            ).collect {
                when (it) {
                    is Response.Success -> {
                        dispatch(Executor.ClearInput)
                    }

                    is Response.Error -> {
                        dispatch(Executor.Error(throwable = it.error))
                    }
                }
            }
        }

        Effect.GetFactByRandomNumber -> {
            getFactAboutRandomNumberUseCase().collect {
                when (it) {
                    is Response.Success -> {}

                    is Response.Error -> {
                        dispatch(Executor.Error(throwable = it.error))
                    }
                }
            }
        }

        Effect.SubscribeOnNumbers -> {
            getCachedFactsUseCase().collect {
                when (it) {
                    is Response.Success -> {
                        dispatch(
                            Executor.ReplaceFacts(
                                factsAboutNumber = it.data
                            )
                        )
                    }

                    is Response.Error -> {
                        dispatch(Executor.Error(throwable = it.error))
                    }
                }
            }
        }
    }

}

