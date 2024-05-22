package com.example.catchallenge.presentation.breedDetails.componnents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BioItem(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String,
    text: String
) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}