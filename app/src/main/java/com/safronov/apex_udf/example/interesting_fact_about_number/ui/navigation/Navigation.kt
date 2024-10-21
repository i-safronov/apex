package com.safronov.apex_udf.example.interesting_fact_about_number.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.numbers_list.NumbersListDestination
import com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.numbers_list.NumbersListRoute

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NumbersListRoute.path
    ) {
        composable(route = NumbersListRoute.path) {
            NumbersListDestination(
                navController = navController
            )
        }
    }
}