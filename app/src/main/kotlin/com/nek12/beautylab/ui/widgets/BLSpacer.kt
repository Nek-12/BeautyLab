package com.nek12.beautylab.ui.widgets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BLSpacer(height: Dp = 12.dp) {
    Spacer(Modifier.height(height))
}

@Composable
@Preview(name = "BLSpacer", showSystemUi = false, showBackground = true)
private fun BLSpacerPreview() {
    BLSpacer()
}
