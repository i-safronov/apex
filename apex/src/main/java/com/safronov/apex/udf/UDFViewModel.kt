package com.safronov.apex.udf

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safronov.apex.dispatchers.DispatchersList
import com.safronov.apex.extension.list.pushBack
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

abstract class UDFViewModel<S : UDF.State, EX : UDF.Executor, EF : UDF.Effect, EV : UDF.Event>(
    initState: S,
    capacity: Int = 3,
    private val dispatchers: DispatchersList = DispatchersList.Base(),
) : ViewModel() {

    private var _state = mutableStateOf(initState)
    val state get() = _state.value

    var events = mutableStateOf<List<EV>>(emptyList())
        private set

    private val executors = Channel<EX>(capacity = capacity)
    private val effectors = Channel<EF>(capacity)

    private var proceedJob: Job
    private var effectorJob: Job

    init {
        proceedJob = proceed()
        effectorJob = effector()
    }

    fun dispatch(vararg ex: EX) {
        ex.forEach { executors.trySend(it) }
    }

    fun sendEvent(vararg ev: EV) {
        events.value = events.value.pushBack(ev)
    }

    private fun proceed() = viewModelScope.launch(dispatchers.ui()) {
        while (true) {
            if (isActive) {
                _state.value = execute(ex = executors.receive())
            }
        }
    }

    private fun effector() = viewModelScope.launch(dispatchers.io()) {
        while (true) {
            if (isActive) {
                affect(effectors.receive())
            }
        }
    }

    abstract suspend fun execute(ex: EX): S
    abstract suspend fun affect(effect: EF)

    override fun onCleared() {
        proceedJob.cancel()
        effectorJob.cancel()
        super.onCleared()
    }

}