package com.nek12.beautylab.ui.widgets

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.FloatingActionButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mikepenz.iconics.typeface.IIcon

@Composable
fun BLFab(
    icon: IIcon,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colors.onPrimary,
    backgroundColor: Color = MaterialTheme.colors.secondary,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        shape = CircleShape,
        backgroundColor = backgroundColor,
        elevation = elevation
    ) {
        BLIcon(asset = icon, tint = iconTint)
    }
}
