package com.safronov.apex.example.interesting_fact_about_number.ui.composable.numbers_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FactItem(
    modifier: Modifier = Modifier,
    number: String,
    text: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                top = 8.dp,
            )
            .clickable(enabled = true, onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )

        Column(
            modifier = Modifier
                .padding(
                    vertical = 4.dp,
                    horizontal = 8.dp
                )
        ) {
            Text(
                text = number,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text(
                text = text,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )
    }
}

@Composable
@Preview
fun FactItemPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn {
            items(100) {
                FactItem(
                    number = "$it",
                    text = "Some interesting fact about number",
                    onClick = {}
                )
            }
        }
    }
}