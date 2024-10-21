package com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.numbers_list

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
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.safronov.apex_udf.example.interesting_fact_about_number.domain.model.fact.output.FactAboutNumber
import com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.numbers_list.components.FactItem

@Composable
fun NumbersListScreen(
    modifier: Modifier = Modifier,
    state: NumbersListContract.State,
    dispatch: (NumbersListContract.Executor) -> Unit
) {
    val factsState = rememberLazyListState()
    Column(
        modifier = modifier,
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
                modifier = Modifier.fillMaxWidth().padding(top = 6.dp),
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
                    text = fact.fact
                )
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

            }
        )
    }
}