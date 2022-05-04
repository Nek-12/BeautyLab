package com.nek12.beautylab.ui.widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mikepenz.iconics.typeface.IIcon
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.common.GMRIcon


@Composable
fun BLTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigationIconClick: (() -> Unit)? = null,
    navigationIcon: IIcon = GMRIcon.gmr_arrow_back,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        contentColor = MaterialTheme.colors.primary,
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        actions = actions,
        navigationIcon = onNavigationIconClick?.let { { BLIcon(asset = navigationIcon, onClick = it) } },
        title = {
            Text(
                title,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                softWrap = true,
                maxLines = 2
            )
        }
    )
}


@Composable
fun BLTopBar(
    title: Int,
    modifier: Modifier = Modifier,
    onNavigationIconClick: (() -> Unit)? = null,
    navigationIcon: IIcon = GMRIcon.gmr_arrow_back,
    actions: @Composable RowScope.() -> Unit = {}
) = BLTopBar(title.string(), modifier, onNavigationIconClick, navigationIcon, actions)
