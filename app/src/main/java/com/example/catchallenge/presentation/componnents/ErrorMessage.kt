package com.example.catchallenge.presentation.componnents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.catchallenge.R

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    message: String,
    retry: Boolean = false,
    onClick: () -> Unit = {}
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = message,
            fontWeight = FontWeight.SemiBold,
        )
        if (retry)
        {
            IconButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { onClick() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = stringResource(id = R.string.retry),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}