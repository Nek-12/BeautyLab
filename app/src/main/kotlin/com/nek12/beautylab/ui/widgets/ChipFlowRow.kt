package com.nek12.beautylab.ui.widgets

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> ChipFlowRow(
    chips: List<T>,
    contentSelector: @Composable (T) -> String,
    onClick: (T) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: @Composable (T) -> Boolean = { false },
) {
    FlowRow(mainAxisSpacing = 4.dp, crossAxisSpacing = 4.dp, modifier = modifier) {
        chips.forEach {
            FilterChip(onClick = { onClick(it) }, selected = isSelected(it)) {
                Text(contentSelector(it), style = MaterialTheme.typography.caption)
            }
        }
    }
}


@Composable
@Preview(name = "ChipFlowRow", showSystemUi = false, showBackground = true)
private fun ChipFlowRowPreview() {
    ChipFlowRow(listOf("One", "Two", "Three"), { it }, {})
}
