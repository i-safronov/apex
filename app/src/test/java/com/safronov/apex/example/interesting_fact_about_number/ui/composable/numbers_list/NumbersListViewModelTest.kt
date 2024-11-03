package com.safronov.apex.example.interesting_fact_about_number.ui.composable.numbers_list

import com.safronov.apex.example.interesting_fact_about_number.domain.model.fact.input.GetFactAboutNumberInput
import com.safronov.apex.example.interesting_fact_about_number.domain.model.fact.output.FactAboutNumber
import com.safronov.apex.example.interesting_fact_about_number.domain.response.Response
import com.safronov.apex.example.interesting_fact_about_number.domain.use_case.GetCachedFactsUseCase
import com.safronov.apex.example.interesting_fact_about_number.domain.use_case.GetFactAboutNumberUseCase
import com.safronov.apex.example.interesting_fact_about_number.domain.use_case.GetFactAboutRandomNumberUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class NumbersListViewModelTest {

    private lateinit var vm: NumbersListViewModel
    private val getFactAboutNumberUseCase = mock<GetFactAboutNumberUseCase>()
    private val getFactAboutRandomNumberUseCase = mock<GetFactAboutRandomNumberUseCase>()
    private val getCachedFactsUseCase = mock<GetCachedFactsUseCase>()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        Mockito.`when`(getCachedFactsUseCase.invoke()).thenReturn(
            flow { emit(Response.Success(data = emptyList())) }
        )

        vm = NumbersListViewModel(
            getFactAboutNumberUseCase = getFactAboutNumberUseCase,
            getFactAboutRandomNumberUseCase = getFactAboutRandomNumberUseCase,
            getCachedFactsUseCase = getCachedFactsUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init state should set loading to true`() {
        assertEquals(true, vm.state.isLoading)
    }

    @Test
    fun `Executor Init should trigger loading and subscribe to cached facts`() {
        vm.dispatch(NumbersListContract.Executor.Init)

        assertEquals(true, vm.state.isLoading)
    }

    @Test
    fun `Executor ReplaceFacts should update facts and set loading to false`() {
        val facts = listOf(
            FactAboutNumber(1, number = 1, fact = "Number 1"),
            FactAboutNumber(2, 2, "Number 2")
        )
        vm.dispatch(NumbersListContract.Executor.ReplaceFacts(facts))

        assertEquals(false, vm.state.isLoading)
        assertEquals(facts, vm.state.facts)
    }

    @Test
    fun `Executor GetFactByNumber should set obtaining fact to true`() {
        val thenReturn = `when`(getFactAboutNumberUseCase.invoke(any())).thenReturn(
            flow { emit(Response.Success(data = Unit)) }
        )
        val validInput = "42"
        vm.dispatch(NumbersListContract.Executor.InputChanged(validInput))

        vm.dispatch(NumbersListContract.Executor.GetFactByNumber)

        verify(getFactAboutNumberUseCase).invoke(GetFactAboutNumberInput(number = 42))
    }

    @Test
    fun `Executor GetFactByNumber with invalid input should update inputError`() {
        val invalidInput = "invalid"
        vm.dispatch(NumbersListContract.Executor.InputChanged(invalidInput))

        vm.dispatch(NumbersListContract.Executor.GetFactByNumber)

        assertNotNull(vm.state.inputError)
    }

    @Test
    fun `Executor GetFactByRandomNumber should set obtaining fact to true`() {
        vm.dispatch(NumbersListContract.Executor.GetFactByRandomNumber)
        verify(getFactAboutRandomNumberUseCase).invoke()
    }

    @Test
    fun `Executor Error should set loading to false and show error message`() {
        val throwable = Throwable("Test error")
        vm.dispatch(NumbersListContract.Executor.Error(throwable))

        assertEquals(false, vm.state.isLoading)
    }

    @Test
    fun `Executor OnFactClick should send navigation event`() {
        val fact = FactAboutNumber(1, number = 1, fact = "Fact 1")
        vm.dispatch(NumbersListContract.Executor.OnFactClick(fact))

    }

    @Test
    fun `Executor InputChanged should update input and clear error`() {
        val newInput = "new input"
        vm.dispatch(NumbersListContract.Executor.InputChanged(newInput))

        assertEquals(newInput, vm.state.input)
        assertNull(vm.state.inputError)
    }

    @Test
    fun `Executor ClearInput should reset input to empty`() {
        vm.dispatch(NumbersListContract.Executor.ClearInput)

        assertEquals("", vm.state.input)
    }

    @Test
    fun `Effect SubscribeOnNumbers should emit facts and update state`() {
        val facts = listOf(FactAboutNumber(1, number = 1, "Fact 1"))
        `when`(getCachedFactsUseCase.invoke()).thenReturn(flow {
            emit(Response.Success(facts))
        })
        verify(getCachedFactsUseCase).invoke()
    }
}
