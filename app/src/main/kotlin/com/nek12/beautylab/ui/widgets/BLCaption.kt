package com.nek12.beautylab.ui.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.nek12.androidutils.compose.string

@Composable
fun BLCaption(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
) {
    Text(
        text = text,
        color = color,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        style = MaterialTheme.typography.caption,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun BLCaption(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
) = BLCaption(text.string(), modifier, color)

@Composable
@Preview(name = "BLCaption", showSystemUi = false, showBackground = true)
private fun BLCaptionPreview() {
    BLCaption("Test Caption")
}
