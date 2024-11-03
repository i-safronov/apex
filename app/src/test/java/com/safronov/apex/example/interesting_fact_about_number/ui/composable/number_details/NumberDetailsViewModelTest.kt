package com.safronov.apex.example.interesting_fact_about_number.ui.composable.number_details

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NumberDetailsViewModelTest {

    private lateinit var vm: NumberDetailsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        vm = NumberDetailsViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun setProperties() {
        assertEquals(true, vm.state.number.isEmpty())
        assertEquals(true, vm.state.description.isEmpty())
        val number = "number"
        val description = "description"
        vm.dispatch(NumberDetailsContract.Executor.SetProperties(
            number = number,
            description = description
        ))
        assertEquals(true, vm.state.number == number    )
        assertEquals(true, vm.state.description == description)
    }

}