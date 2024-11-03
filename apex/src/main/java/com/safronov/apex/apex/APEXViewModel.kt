package com.safronov.apex.apex

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov.apex.dispatchers.DispatchersList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

interface ExecutorScope<EF : APEX.Effect, EV: APEX.Event> {
    fun sendEffect(ef: EF)
    fun sendEvent(event: EV)
}

interface EffectorScope<EX : APEX.Executor> {
    fun dispatch(vararg ex: EX)
}

inline fun <EV : APEX.Event> Channel<EV>.onEvent(
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

abstract class APEXViewModel<S : APEX.State, EX : APEX.Executor, EF : APEX.Effect, EV : APEX.Event>(
    initState: S,
    capacity: Int = 3,
    private val dispatchers: DispatchersList = DispatchersList.Base(),
) : ViewModel() {

    private var _state = mutableStateOf(initState)
    val state get() = _state.value

    val events = Channel<EV>(capacity)
    private val executors = Channel<EX>(capacity)
    private val effectors = Channel<EF>(capacity)

    private var proceedJob: Job
    private var effectorJob: Job

    init {
        proceedJob = proceed()
        effectorJob = effector()
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

    private inner class ExecutorScopeImpl : ExecutorScope<EF, EV> {
        override fun sendEffect(ef: EF) {
            effectors.trySend(ef)
        }

        override fun sendEvent(event: EV) {
            events.trySend(event)
        }
    }

    private inner class EffectorScopeImpl : EffectorScope<EX> {
        override fun dispatch(vararg ex: EX) {
            ex.forEach { executors.trySend(it) }
        }
    }

    protected abstract suspend fun ExecutorScope<EF, EV>.execute(ex: EX): S

    protected abstract suspend fun EffectorScope<EX>.affect(ef: EF)

    override fun onCleared() {
        proceedJob.cancel()
        effectorJob.cancel()
        super.onCleared()
    }
}
