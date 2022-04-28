package com.nek12.beautylab.ui.widgets

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikepenz.iconics.typeface.IIcon
import com.nek12.beautylab.common.GMRIcon

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BLTwoFabsLayout(
    firstIcon: IIcon,
    secondIcon: IIcon,
    onFirstClick: () -> Unit,
    onSecondClick: () -> Unit,
    modifier: Modifier = Modifier,
    firstVisible: Boolean = true,
    secondVisible: Boolean = true,
    firstColor: Color = MaterialTheme.colors.error,
    secondColor: Color = MaterialTheme.colors.primary,
) {
    AnimatedContent(firstVisible to secondVisible) { (firstVisible, secondVisible) ->
        Row(
            Modifier
                .fillMaxWidth()
                .then(modifier),
            Arrangement.SpaceEvenly,
            Alignment.CenterVertically
        ) {
            if (firstVisible) {
                BLFab(
                    icon = firstIcon,
                    onClick = onFirstClick,
                    iconTint = MaterialTheme.colors.onError,
                    modifier = Modifier.padding(12.dp),
                    backgroundColor = firstColor
                )
            }
            if (secondVisible) {
                BLFab(
                    icon = secondIcon,
                    onClick = onSecondClick,
                    iconTint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(12.dp),
                    backgroundColor = secondColor
                )
            }
        }
    }
}

@Composable
@Preview(name = "BLTwoFabsLayout", showSystemUi = false, showBackground = true)
private fun BLTwoFabsLayoutPreview() {
    Column {
        BLTwoFabsLayout(GMRIcon.gmr_delete, GMRIcon.gmr_check, {}, {})
        BLTwoFabsLayout(GMRIcon.gmr_delete, GMRIcon.gmr_check, {}, {}, firstVisible = false)
        BLTwoFabsLayout(GMRIcon.gmr_delete, GMRIcon.gmr_check, {}, {}, secondVisible = false)
        Divider(Modifier.fillMaxWidth())
        BLTwoFabsLayout(GMRIcon.gmr_delete, GMRIcon.gmr_check, {}, {}, secondVisible = false, firstVisible = false)
    }
}
