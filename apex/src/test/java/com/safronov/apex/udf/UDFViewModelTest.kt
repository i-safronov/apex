package com.safronov.apex.udf

import com.safronov.apex.shared.TestEffect
import com.safronov.apex.shared.TestEvent
import com.safronov.apex.shared.TestExecutor
import com.safronov.apex.shared.TestState
import com.safronov.apex.shared.TestUDFViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UDFViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is set correctly`() = runTest {
        val initialState = TestState()
        val viewModel = TestUDFViewModel(initialState)
        assertEquals(initialState, viewModel.state.value)
    }

    @Test
    fun `initial events list is empty`() = runTest {
        val viewModel = TestUDFViewModel(TestState())
        assertTrue(viewModel.events.value.isEmpty())
    }

    @Test
    fun `single event is added correctly`() = runTest {
        val viewModel = TestUDFViewModel(TestState())
        viewModel.sendEvent(TestEvent())
        assertEquals(1, viewModel.events.value.size)
    }

    @Test
    fun `multiple events are added correctly`() = runTest {
        val viewModel = TestUDFViewModel(TestState())
        viewModel.sendEvent(TestEvent(), TestEvent())
        assertEquals(2, viewModel.events.value.size)
    }

    @Test
    fun `executor command changes state correctly`() = runTest {
        val initialState = TestState()
        val updatedState = TestState(updated = true)
        val viewModel = TestUDFViewModel(initialState, updatedState = updatedState)
        viewModel.dispatch(TestExecutor())
        advanceUntilIdle()
        assertEquals(updatedState, viewModel.state.value)
    }

    @Test
    fun `executor processes command and changes state`() = runTest {
        val initialState = TestState()
        val updatedState = TestState(updated = true)
        val viewModel = TestUDFViewModel(initialState, updatedState = updatedState)

        viewModel.dispatch(TestExecutor())
        advanceUntilIdle()

        assertEquals(updatedState, viewModel.state.value)
    }

    @Test
    fun `effect is processed correctly`() = runTest {
        val viewModel = TestUDFViewModel(TestState())
        assertFalse(viewModel.effectHandled)
        val effect = TestEffect()
        viewModel.affect(effect)

        advanceUntilIdle()

        assertTrue(viewModel.effectHandled)
        assertEquals(viewModel.effect, effect)
    }

    @Test
    fun `jobs are cancelled on viewModel clear`() = runTest {
        val viewModel = TestUDFViewModel(TestState())
        viewModel.clearViewModel()
        assertTrue(viewModel.isProceedJobCancelled)
        assertTrue(viewModel.isEffectorJobCancelled)
    }

    @Test
    fun `state is updated after executor processes`() = runTest {
        val viewModel = TestUDFViewModel(TestState())
        viewModel.dispatch(TestExecutor())
        advanceUntilIdle()
        assertNotEquals(TestState(), viewModel.state.value)
    }


}