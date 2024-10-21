package com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.numbers_list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

class NumbersListRoute {
    companion object {
        val path = "numbers_list"
    }
}

@Composable
fun NumbersListDestination(
    modifier: Modifier = Modifier,
    navController: NavController
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