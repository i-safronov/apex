package com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.numbers_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.numbers_list.components.FactItem

@Composable
fun NumbersListContract(
    modifier: Modifier = Modifier,
    state: NumbersListContract.State,
    dispatch: (NumbersListContract.Executor) -> Unit
) {
    val factsState = rememberLazyListState()
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = state.input,
            onValueChange = { dispatch(NumbersListContract.Executor.InputChanged(input = it)) },
            placeholder = {
                Text("Enter number")
            }
        )

        Button(
            onClick = {
                dispatch(NumbersListContract.Executor.GetFactByNumber)
            }
        ) {
            Text("Get fact")
        }

        Button(
            onClick = {
                dispatch(NumbersListContract.Executor.GetFactByRandomNumber)
            }
        ) {
            Text("Get fact about random number")
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