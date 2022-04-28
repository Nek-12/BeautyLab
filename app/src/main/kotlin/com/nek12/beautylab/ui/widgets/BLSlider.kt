package com.nek12.beautylab.ui.widgets

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SliderWithLabel(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    finiteEnd: Boolean,
    modifier: Modifier = Modifier,
    labelMinWidth: Dp = 20.dp,
) {

    Column(modifier = modifier) {

        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {

            val offset by animateDpAsState(
                targetValue = getSliderOffset(
                    value = value,
                    valueRange = valueRange,
                    boxWidth = maxWidth,
                    labelWidth = labelMinWidth + 4.dp // Since we use a padding of 2.dp on either sides of the SliderLabel, we need to account for this in our calculation
                )
            )

            val endValueText =
                if (!finiteEnd && value >= valueRange.endInclusive) "${value.toInt()}+" else value.toInt()
                    .toString()

            //label
            Text(
                endValueText,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.caption,
                overflow = TextOverflow.Ellipsis,
                softWrap = false,
                maxLines = 1,
                modifier = Modifier
                    .padding(start = offset)
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = MaterialTheme.shapes.small,
                    )
                    .padding(2.dp)
                    .defaultMinSize(minWidth = labelMinWidth)
            )
        }

        Slider(
            value = value, onValueChange = onValueChange,
            valueRange = valueRange,
            modifier = Modifier.fillMaxWidth()
        )

    }
}

private fun getSliderOffset(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    boxWidth: Dp,
    labelWidth: Dp,
): Dp {

    val coerced = value.coerceIn(valueRange.start, valueRange.endInclusive)
    val positionFraction = calcFraction(valueRange.start, valueRange.endInclusive, coerced)

    return (boxWidth - labelWidth) * positionFraction
}

// Calculate the 0..1 fraction that `pos` value represents between `a` and `b`
private fun calcFraction(a: Float, b: Float, pos: Float) =
    (if (b - a == 0f) 0f else (pos - a) / (b - a)).coerceIn(0f, 1f)
