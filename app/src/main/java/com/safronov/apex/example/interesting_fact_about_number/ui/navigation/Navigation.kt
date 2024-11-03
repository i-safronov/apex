package com.safronov.apex.example.interesting_fact_about_number.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.safronov.apex.example.interesting_fact_about_number.ui.composable.number_details.NumberDetailsDestination
import com.safronov.apex.example.interesting_fact_about_number.ui.composable.number_details.NumberDetailsRoute
import com.safronov.apex.example.interesting_fact_about_number.ui.composable.numbers_list.NumbersListDestination
import com.safronov.apex.example.interesting_fact_about_number.ui.composable.numbers_list.NumbersListRoute

fun withArgs(route: String, vararg args: String): String {
    return buildString {
        append(route)
        args.forEach { arg -> append("/$arg") }
    }
}

@Composable
fun Navigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NumbersListRoute.path
    ) {
        composable(
            route = NumbersListRoute.path
        ) {
            NumbersListDestination(
                navigateToNumberDetails = { args ->
                    navController.navigate(
                        withArgs(
                            route = NumberDetailsRoute.path,
                            args = arrayOf(args.number, args.description)
                        )
                    )
                }
            )
        }

        composable(
            route = "${NumberDetailsRoute.path}/{${NumberDetailsRoute.Args.NUMBER.name}}/{${NumberDetailsRoute.Args.DESCRIPTION.name}}",
            arguments = listOf(
                navArgument(name = NumberDetailsRoute.Args.NUMBER.name) {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = false
                },
                navArgument(name = NumberDetailsRoute.Args.DESCRIPTION.name) {
                    type = NavType.StringType
                    defaultValue =
                        "If a reference points to null, it simply means that there is no value associated with it.\n" +
                                "\n" +
                                "Technically speaking, the memory location assigned to the reference contains the value 0 (all bits at zero), or any other value that denotes null in the given environment."
                    nullable = false
                },
            )
        ) { entry ->
            NumberDetailsDestination(
                number = entry.arguments?.getString(NumberDetailsRoute.Args.NUMBER.name) ?: "",
                description = entry.arguments?.getString(NumberDetailsRoute.Args.DESCRIPTION.name)
                    ?: "",
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}