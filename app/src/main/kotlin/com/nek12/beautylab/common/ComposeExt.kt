package com.nek12.beautylab.common

import android.content.Context
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.typeface.ITypeface
import com.mikepenz.iconics.typeface.library.googlematerial.RoundedGoogleMaterial
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

typealias GMRIcon = RoundedGoogleMaterial.Icon

val surfaceTint @Composable get() = ColorFilter.tint(MaterialTheme.colors.onSurface)

fun ITypeface.randomIcon(): IIcon = getIcon(characters.keys.random()) //use static lazy map provided by ITypeface

fun CoroutineScope.snackbar(
    text: String,
    scaffoldState: ScaffoldState,
    duration: SnackbarDuration = SnackbarDuration.Short,
) = launch {
    scaffoldState.snackbarHostState.showSnackbar(text, duration = duration)
}

fun CoroutineScope.snackbar(
    context: Context,
    text: Text,
    scaffoldState: ScaffoldState,
    duration: SnackbarDuration = SnackbarDuration.Short,
) = snackbar(text.string(context), scaffoldState, duration)
