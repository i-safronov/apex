package com.safronov.apex.example.interesting_fact_about_number.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import com.safronov.apex.example.interesting_fact_about_number.ui.navigation.Navigation

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Navigation(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}