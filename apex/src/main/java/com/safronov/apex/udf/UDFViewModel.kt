package com.safronov.apex.udf

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov.apex.dispatchers.DispatchersList
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

abstract class UDFViewModel<S : UDF.State, EX : UDF.Executor, EF : UDF.Effect, EV : UDF.Event>(
    initState: S,
    capacity: Int = 3,
    private val dispatchers: DispatchersList = DispatchersList.Base(),
) : ViewModel() {

    private val executors = Channel<EX>(capacity = capacity)
    private val effectors = Channel<EF>(capacity)

    private var proceedJob: Job

    init {
        proceedJob = proceed()
    }

    private fun channel(capacity: Int) = Channel<EF>(capacity = capacity)
    var state = mutableStateOf(initState)
        private set

    fun dispatch(ex: EX) {
        executors.trySend(ex)
    }

    private fun proceed() = viewModelScope.launch(dispatchers.ui()) {
        while (true) {
            if (isActive) {
                state.value = execute(ex = executors.receive())
            }
        }
    }

    abstract suspend fun execute(ex: EX): S
    abstract suspend fun affect(effect: EF): EX

}