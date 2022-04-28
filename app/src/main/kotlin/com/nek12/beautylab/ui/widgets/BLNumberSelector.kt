package com.nek12.beautylab.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nek12.beautylab.common.GMRIcon
import com.nek12.beautylab.ui.theme.BeautyLabTheme

@Composable
fun BLNumberSelector(
        number: Int,
        onPlus: () -> Unit,
        onMinus: () -> Unit,
        modifier: Modifier = Modifier,
        plusEnabled: Boolean = true,
        minusEnabled: Boolean = true,
        buttonSizes: Dp = 32.dp,
        onClickText: () -> Unit = {},
) {
    val plusColor = if (plusEnabled) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
    val minusColor = if (minusEnabled) MaterialTheme.colors.error else MaterialTheme.colors.onSurface

    Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = modifier
    ) {
        BLCircleIcon(
                icon = GMRIcon.gmr_remove,
                color = minusColor,
                size = buttonSizes,
                onClick = onMinus,
                modifier = Modifier.padding(1.dp)
        )

        Text(
                number.toString(),
                modifier = Modifier
                    .clickable(onClick = onClickText)
                    .padding(12.dp),
                style = MaterialTheme.typography.body1,
        )

        BLCircleIcon(
                icon = GMRIcon.gmr_add,
                color = plusColor,
                size = buttonSizes,
                onClick = onPlus,
                modifier = Modifier.padding(1.dp)
        )
    }
}


@Composable
@Preview(name = "BLNumberSelector", showSystemUi = false, showBackground = true)
private fun BLNumberSelectorPreview() {
    BeautyLabTheme(true) {
        BLNumberSelector(
                number = 17,
                onPlus = {},
                onMinus = {},
                modifier = Modifier,
                plusEnabled = true,
                minusEnabled = true
        )
    }
}
