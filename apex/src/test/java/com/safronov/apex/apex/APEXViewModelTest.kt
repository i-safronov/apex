package com.safronov.apex.apex

import com.safronov.apex.shared.TestExecutor
import com.safronov.apex.shared.TestState
import com.safronov.apex.shared.TestAPEXViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class APEXViewModelTest {

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
        val viewModel = TestAPEXViewModel(initialState)
        assertEquals(initialState, viewModel.state)
    }

    @Test
    fun `executor command changes state correctly`() = runTest {
        val initialState = TestState()
        val updatedState = TestState(updated = true)
        val viewModel = TestAPEXViewModel(initialState, updatedState = updatedState)
        viewModel.dispatch(TestExecutor())
        advanceUntilIdle()
        assertEquals(updatedState, viewModel.state)
    }

    @Test
    fun `executor processes command and changes state`() = runTest {
        val initialState = TestState()
        val updatedState = TestState(updated = true)
        val viewModel = TestAPEXViewModel(initialState, updatedState = updatedState)

        viewModel.dispatch(TestExecutor())
        advanceUntilIdle()

        assertEquals(updatedState, viewModel.state)
    }

    @Test
    fun `jobs are cancelled on viewModel clear`() = runTest {
        val viewModel = TestAPEXViewModel(TestState())
        viewModel.clearViewModel()
        assertTrue(viewModel.isProceedJobCancelled)
        assertTrue(viewModel.isEffectorJobCancelled)
    }

    @Test
    fun `state is updated after executor processes`() = runTest {
        val viewModel = TestAPEXViewModel(TestState())
        viewModel.dispatch(TestExecutor())
        advanceUntilIdle()
        assertNotEquals(TestState(), viewModel.state)
    }


}