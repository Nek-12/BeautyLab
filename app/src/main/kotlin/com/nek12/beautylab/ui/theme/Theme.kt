package com.nek12.beautylab.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val LightColors = lightColors(
    primary = purple,
    primaryVariant = purple,
    secondary = coral,
    secondaryVariant = coral,
//    text = bright_red,
    onPrimary = Color.White,
    onSecondary = Color.White,
//    background = Color.White,
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White
)

val DarkColors = darkColors(
    primary = purple,
    primaryVariant = purple,
    secondary = coral,
    secondaryVariant = coral,
//    text = bright_red,
    onPrimary = Color.White,
    onSecondary = Color.White,
//    background = Color.Black,
//    surface = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.White
)

val AppTypography = Typography(
//    defaultFontFamily = Comfortaa,
)

val shapes = Shapes(
    small = RoundedCornerShape(50),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp),
)


@Composable
fun BeautyLabTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (useDarkTheme) DarkColors else LightColors,
        typography = AppTypography,
        content = content,
        shapes = shapes
    )
}
