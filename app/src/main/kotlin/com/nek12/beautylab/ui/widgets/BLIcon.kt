package com.nek12.beautylab.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mikepenz.iconics.compose.Image
import com.mikepenz.iconics.typeface.IIcon
import com.nek12.beautylab.common.GMBLIcon

@Composable
fun BLIcon(
    asset: IIcon,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    size: Dp = 28.dp,
    tint: Color = MaterialTheme.colors.onSurface,
) {
    Image(
        asset = asset,
        modifier = modifier
            .padding(2.dp)
            .size(size)
            .then(onClick?.let { Modifier.clickable(onClick = onClick) } ?: Modifier),
        alignment = Alignment.Center,
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(tint),
    )
}

@Composable
@Preview
private fun BLIconPreview() {
    BLIcon(asset = GMBLIcon.gmr_check)
}
