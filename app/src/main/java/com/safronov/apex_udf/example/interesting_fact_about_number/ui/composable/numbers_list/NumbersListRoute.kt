package com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.numbers_list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.number_details.NumberDetailsRoute

class NumbersListRoute {
    companion object {
        val path = "numbers_list"
    }
}

@Composable
fun NumbersListDestination(
    modifier: Modifier = Modifier,
    navigateToNumberDetails: (NumberDetailsRoute.Arg) -> Unit,
) {
    val viewModel: NumbersListViewModel = hiltViewModel()

    NumbersListScreen(
        modifier = modifier,
        state = viewModel.state,
        navigateToNumberDetails = navigateToNumberDetails,
        events = viewModel.events,
        dispatch = {
            viewModel.dispatch(it)
        }
    )
}