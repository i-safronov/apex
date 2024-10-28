package com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.number_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

class NumberDetailsRoute {
    companion object {
        val path = "number_details"
    }

    enum class Args {
        NUMBER, DESCRIPTION
    }

    data class Arg(
        val number: String,
        val description: String
    )
}

@Composable
fun NumberDetailsDestination(
    modifier: Modifier = Modifier,
    number: String,
    description: String
) {
    val viewModel: NumberDetailsViewModel = hiltViewModel()

    LaunchedEffect(
        number, description
    ) {
        viewModel.dispatch(
            NumberDetailsContract.Executor.SetProperties(
                number = number,
                description = description
            )
        )
    }

    NumberDetailsScreen(
        modifier = modifier,
        state = viewModel.state
    )
}