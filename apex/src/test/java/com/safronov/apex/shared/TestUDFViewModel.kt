package com.safronov.apex.shared

import com.safronov.apex.dispatchers.DispatchersList
import com.safronov.apex.udf.EffectorScope
import com.safronov.apex.udf.ExecutorScope
import com.safronov.apex.udf.UDF
import com.safronov.apex.udf.UDFViewModel

internal data class TestState(val updated: Boolean = false) : UDF.State
internal class TestExecutor : UDF.Executor
internal class TestEffect : UDF.Effect
internal class TestEvent : UDF.Event

internal class TestUDFViewModel(
    initState: TestState,
    private val updatedState: TestState = TestState(updated = true),
    dispatchers: DispatchersList = DispatchersList.Base(),
    capacity: Int = 3
) : UDFViewModel<TestState, TestExecutor, TestEffect, TestEvent>(initState = initState, dispatchers = dispatchers, capacity = capacity) {

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