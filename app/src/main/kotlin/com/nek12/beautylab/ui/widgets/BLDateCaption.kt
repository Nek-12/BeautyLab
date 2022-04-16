package com.nek12.beautylab.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle.LONG

@Composable
fun BLDateCaption(
    date: ZonedDateTime,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
) {
    val formatter = remember { DateTimeFormatter.ofLocalizedDateTime(LONG) }
    val text = formatter.format(date.toLocalDateTime())

    BLCaption(R.string.created_at_template.string(text), modifier, color)
}

@Composable
@Preview(name = "BLDateCaption", showSystemUi = false, showBackground = true)
private fun BLDateCaptionPreview() {
    BLDateCaption(ZonedDateTime.now())
}
