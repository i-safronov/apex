package com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.numbers_list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NumbersListRoute(
    modifier: Modifier = Modifier
) {
    val viewModel: NumbersListViewModel = hiltViewModel()

    NumbersListScreen(
        modifier = modifier,
        state = viewModel.state,
        dispatch = {
            viewModel.dispatch(it)
        }
    )
}