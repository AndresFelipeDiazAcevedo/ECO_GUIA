
package me.andresdiaz.eco_guia.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = EcoGreen,
    onPrimary = EcoWhite,

    secondary = EcoMint,
    onSecondary = EcoGreen,

    tertiary = EcoNavy,
    onTertiary = EcoWhite,

    background = EcoBackground,
    onBackground = EcoNavy,

    surface = EcoWhite,
    onSurface = EcoNavy,

    surfaceVariant = EcoLavender,
    onSurfaceVariant = EcoGray,

    outline = EcoLightGray,
    error = EcoRed,
    onError = EcoWhite
)

private val DarkColorScheme = darkColorScheme(
    primary = EcoMint,
    onPrimary = EcoGreen,

    secondary = Color(0xFF9AEFB1),
    onSecondary = EcoGreen,

    tertiary = Color(0xFFB8C6F0),
    onTertiary = EcoNavy,

    background = Color(0xFF101C19),
    onBackground = EcoWhite,

    surface = Color(0xFF192923),
    onSurface = EcoWhite,

    surfaceVariant = Color(0xFF263A32),
    onSurfaceVariant = Color(0xFFD0D9D3),

    outline = Color(0xFF53665B),
    error = Color(0xFFFF7777),
    onError = Color(0xFF370B0B)
)

@Composable
fun ECO_GUIATheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
