package com.nek12.beautylab.ui.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mikepenz.iconics.typeface.IIcon
import com.nek12.beautylab.common.GMRIcon

@Composable
fun BLCircleIcon(
    icon: IIcon,
    modifier: Modifier = Modifier,
    color: Color,
    elevation: Dp = 0.dp,
    size: Dp = 44.dp,
    onClick: (() -> Unit)? = null,
) {
    Surface(
        modifier = modifier
            .size(size),
        shape = CircleShape,
        color = color.copy(alpha = 0.2f),
        contentColor = color,
        elevation = elevation,
        border = null,
        content = { BLIcon(asset = icon, tint = color, modifier = Modifier.padding(4.dp), onClick = onClick) },
    )
}

@Composable
@Preview(name = "BLCircleIcon", showSystemUi = false, showBackground = true)
private fun BLCircleIconPreview() {
    BLCircleIcon(color = Color.Blue, icon = GMRIcon.gmr_lightbulb_outline)
}
