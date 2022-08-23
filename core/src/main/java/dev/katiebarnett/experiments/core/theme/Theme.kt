package dev.katiebarnett.experiments.core.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat


// Palette
val Purple = Color(0xff781C68)
val PurpleLight = Color(0xffa94d96)
val PurpleDark = Color(0xff49003d)
val Teal = Color(0xff319DA0)
val TealLight = Color(0xff6aced1)
val TealDark = Color(0xff006e72)
val Buff = Color(0xffFFD39A)
val BuffLight = Color(0xffFFF5E1)
val Grey = Color(0xffE1E2E1)
val GreyLight = Color(0xffF5F5F6)

private val colorScheme = lightColorScheme(
    primary = Purple,
    secondary = TealLight,
    tertiary = Buff,
    background = GreyLight,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun ExperimentsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}