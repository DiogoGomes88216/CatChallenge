package com.example.catchallenge.presentation.breedDetails.componnents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BioItem(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    imageSize: Dp = 24.dp,
    contentDescription: String,
    fontWeight: FontWeight = FontWeight.SemiBold,
    fontSize: TextUnit = 24.sp,
    spacedBy: Dp = 8.dp,
    text: String
) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacedBy)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier.size(imageSize)
        )
        Text(
            text = text,
            fontWeight = fontWeight,
            fontSize = fontSize,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}