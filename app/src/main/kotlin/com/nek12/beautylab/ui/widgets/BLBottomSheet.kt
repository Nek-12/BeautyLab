package com.nek12.beautylab.ui.widgets

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun BLBottomSheet(
    navigator: BottomSheetNavigator,
    modifier: Modifier = Modifier,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    sheetContentColor: Color = contentColorFor(sheetBackgroundColor),
    content: (@Composable () -> Unit),
) {
    ModalBottomSheetLayout(
        bottomSheetNavigator = navigator,
        modifier = modifier,
        sheetBackgroundColor = sheetBackgroundColor,
        sheetContentColor = sheetContentColor,
        content = content,
        scrimColor = Color.Black.copy(alpha = 0.5f), //Do not use white screen for dark theme
    )
}
