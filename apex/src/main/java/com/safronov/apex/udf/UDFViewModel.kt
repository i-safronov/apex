package com.safronov.apex.udf

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov.apex.dispatchers.DispatchersList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

interface ExecutorScope<EF : UDF.Effect> {
    fun sendEffect(ef: EF)
}

interface EffectorScope<EX : UDF.Executor> {
    fun dispatch(vararg ex: EX)
}

inline fun <EV: UDF.Event> Channel<EV>.onEvent(
    crossinline block: (EV) -> Unit,
    scope: CoroutineScope = CoroutineScope(Job()),
    dispatchers: DispatchersList = DispatchersList.Base()
) = scope.launch(dispatchers.ui()) {
    while (true) {
        if (isActive) {
            block(receive())
        }
    }
}

abstract class UDFViewModel<S : UDF.State, EX : UDF.Executor, EF : UDF.Effect, EV : UDF.Event>(
    initState: S,
    capacity: Int = 3,
    private val dispatchers: DispatchersList = DispatchersList.Base(),
) : ViewModel() {

    private var _state = mutableStateOf(initState)
    val state get() = _state.value

    var events = Channel<EV>(capacity)
        private set

    private val executors = Channel<EX>(capacity)
    private val effectors = Channel<EF>(capacity)

    private var proceedJob: Job
    private var effectorJob: Job

    init {
        proceedJob = proceed()
        effectorJob = effector()
    }

    fun sendEvent(vararg ev: EV) {
        ev.forEach { events.trySend(it) }
    }

    private fun proceed() = viewModelScope.launch(dispatchers.ui()) {
        while (true) {
            if (isActive) {
                _state.value = ExecutorScopeImpl().execute(executors.receive())
            }
        }
    }

    private fun effector() = viewModelScope.launch(dispatchers.io()) {
        while (isActive) {
            val effect = effectors.receive()
            launch(dispatchers.io()) {
                EffectorScopeImpl().affect(effect)
            }
        }
    }

    fun dispatch(vararg ex: EX) {
        ex.forEach { executors.trySend(it) }
    }

    private inner class ExecutorScopeImpl : ExecutorScope<EF> {
        override fun sendEffect(ef: EF) {
            effectors.trySend(ef)
        }
    }

    private inner class EffectorScopeImpl : EffectorScope<EX> {
        override fun dispatch(vararg ex: EX) {
            ex.forEach { executors.trySend(it) }
        }
    }

    protected abstract suspend fun ExecutorScope<EF>.execute(ex: EX): S
    protected abstract suspend fun EffectorScope<EX>.affect(ef: EF)

    override fun onCleared() {
        proceedJob.cancel()
        effectorJob.cancel()
        super.onCleared()
    }
}