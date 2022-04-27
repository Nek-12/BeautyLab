package com.nek12.beautylab.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import com.nek12.beautylab.common.GMRIcon


@Composable
fun BLEmptyView(
    modifier: Modifier = Modifier,
    text: String = R.string.nothing_found.string(),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BLIcon(asset = GMRIcon.gmr_spa, size = 64.dp)
        Text(text, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }
}

@Composable
@Preview("BLEmptyView")
private fun BLEmptyViewPreview() {
    BLEmptyView()
}
