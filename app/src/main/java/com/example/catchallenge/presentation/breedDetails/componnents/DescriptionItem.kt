package com.example.catchallenge.presentation.breedDetails.componnents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DescritpionItem(
    modifier: Modifier = Modifier,
    description: String,
) {
    Column (
        modifier = modifier,
    ) {
        Text(
            text = "Description:",
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = description,
            fontSize = 18.sp,
        )

    }
}