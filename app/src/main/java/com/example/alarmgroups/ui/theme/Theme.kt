package com.example.alarmgroups.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val colorPalette = darkColors(
    primary = orangeLight,
    primaryVariant = orangeDark,

    background = black3,
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)


@Composable
fun AlarmGroupsTheme(content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(black3)

    MaterialTheme(
        colors = colorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

}