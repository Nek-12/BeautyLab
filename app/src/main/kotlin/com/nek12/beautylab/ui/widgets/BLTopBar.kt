package com.nek12.beautylab.ui.widgets

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nek12.androidutils.compose.string

@Composable
fun BLTopBar(
    title: String,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
    ) {
        Text(title, style = MaterialTheme.typography.h5)
    }
}


@Composable
fun BLTopBar(title: Int, modifier: Modifier = Modifier) = BLTopBar(title.string(), modifier)
