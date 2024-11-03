package com.safronov.apex.shared

import com.safronov.apex.dispatchers.DispatchersList
import com.safronov.apex.apex.EffectorScope
import com.safronov.apex.apex.ExecutorScope
import com.safronov.apex.apex.APEX
import com.safronov.apex.apex.APEXViewModel

internal data class TestState(val updated: Boolean = false) : APEX.State
internal class TestExecutor : APEX.Executor
internal class TestEffect : APEX.Effect
internal class TestEvent : APEX.Event

internal class TestAPEXViewModel(
    initState: TestState,
    private val updatedState: TestState = TestState(updated = true),
    dispatchers: DispatchersList = DispatchersList.Base(),
    capacity: Int = 3
) : APEXViewModel<TestState, TestExecutor, TestEffect, TestEvent>(initState = initState, dispatchers = dispatchers, capacity = capacity) {

    var effectProcessed = false
    var effectHandled = false
    var effect: TestEffect? = null
    var isProceedJobCancelled = false
    var isEffectorJobCancelled = false

    fun clearViewModel() {
        isProceedJobCancelled = true
        isEffectorJobCancelled = true
        onCleared()
    }

    override suspend fun ExecutorScope<TestEffect, TestEvent>.execute(ex: TestExecutor): TestState {
        return updatedState
    }

    override suspend fun EffectorScope<TestExecutor>.affect(ef: TestEffect) {
        effectProcessed = true
        effectHandled = true
        effect = ef
    }
}