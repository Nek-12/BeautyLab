package com.nek12.beautylab.common

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.typeface.ITypeface
import com.mikepenz.iconics.typeface.library.googlematerial.RoundedGoogleMaterial

typealias GMBLIcon = RoundedGoogleMaterial.Icon

val surfaceTint @Composable get() = ColorFilter.tint(MaterialTheme.colors.onSurface)

fun ITypeface.randomIcon(): IIcon = getIcon(characters.keys.random()) //use static lazy map provided by ITypeface
