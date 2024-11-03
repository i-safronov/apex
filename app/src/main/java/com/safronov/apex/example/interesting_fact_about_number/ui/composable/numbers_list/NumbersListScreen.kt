package com.safronov.apex.example.interesting_fact_about_number.ui.composable.numbers_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.safronov.apex.apex.onEvent
import com.safronov.apex.example.interesting_fact_about_number.domain.model.fact.output.FactAboutNumber
import com.safronov.apex.example.interesting_fact_about_number.ui.composable.number_details.NumberDetailsRoute
import com.safronov.apex.example.interesting_fact_about_number.ui.composable.numbers_list.components.FactItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

@Composable
fun NumbersListScreen(
    modifier: Modifier = Modifier,
    state: NumbersListContract.State,
    events: Channel<NumbersListContract.Event>,
    navigateToNumberDetails: (NumberDetailsRoute.Arg) -> Unit,
    dispatch: (NumbersListContract.Executor) -> Unit
) {
    val factsState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    events.onEvent(block = {
        when (it) {
            is NumbersListContract.Event.NavigateToFactDetails -> {
                navigateToNumberDetails(
                    NumberDetailsRoute.Arg(
                        number = it.factAboutNumber.number.toString(),
                        description = it.factAboutNumber.fact
                    )
                )
            }

            is NumbersListContract.Event.ShowError -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = it.error,
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }, scope = scope)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            modifier = modifier
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isLoading || state.isObtainingFact) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 8.dp
                    )
            ) {
                Column {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .padding(2.dp),
                        value = state.input,
                        onValueChange = { dispatch(NumbersListContract.Executor.InputChanged(input = it)) },
                        isError = state.inputError != null,
                        placeholder = {
                            Text(text = "Enter number")
                        }
                    )

                    if (state.inputError != null) {
                        Text(text = state.inputError, color = Color.Red)
                    }
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    onClick = {
                        dispatch(NumbersListContract.Executor.GetFactByNumber)
                    },
                    enabled = !state.isLoading && !state.isObtainingFact
                ) {
                    Text("Get fact")
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        dispatch(NumbersListContract.Executor.GetFactByRandomNumber)
                    },
                    enabled = !state.isLoading && !state.isObtainingFact
                ) {
                    Text("Get fact about random number")
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                state = factsState
            ) {
                items(state.facts) { fact ->
                    FactItem(
                        number = fact.number.toString(),
                        text = fact.fact,
                        onClick = {
                            dispatch(NumbersListContract.Executor.OnFactClick(
                                factAboutNumber = FactAboutNumber(
                                    id = fact.id,
                                    number = fact.number,
                                    fact = fact.fact
                                )
                            ))
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun NumbersListScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        NumbersListScreen(
            state = NumbersListContract.State(
                facts = buildList {
                    repeat(100) {
                        add(
                            FactAboutNumber(
                                id = it,
                                number = (it + 1).toLong(),
                                fact = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam eget augue non orci consequat vestibulum. Quisque at tempor diam, dapibus laoreet nisl. Nunc porttitor velit neque. Phasellus sed accumsan massa, nec laoreet diam. Donec non orci nec ex luctus consectetur ac eu dolor. Pellentesque sagittis quam ante, at fermentum justo feugiat a. In at ligula quam. Aenean augue nunc, efficitur id massa tincidunt, mollis hendrerit arcu. Nulla mauris dui, molestie ut elit eu, luctus iaculis augue. Phasellus ut aliquet nisl."
                            )
                        )
                    }
                },
                isObtainingFact = true
            ),
            dispatch = {

            },
            navigateToNumberDetails = {

            },
            events = Channel()
        )
    }
}