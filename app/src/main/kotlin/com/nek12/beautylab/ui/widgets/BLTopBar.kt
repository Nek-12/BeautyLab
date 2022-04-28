package com.nek12.beautylab.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.nek12.androidutils.compose.string

@Composable
fun BLTopBar(
    title: String,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        contentColor = MaterialTheme.colors.primary,
        backgroundColor = Color.Transparent
    ) {
        Text(
            title,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            softWrap = true,
            maxLines = 2
        )
    }
}


@Composable
fun BLTopBar(title: Int, modifier: Modifier = Modifier) = BLTopBar(title.string(), modifier)
