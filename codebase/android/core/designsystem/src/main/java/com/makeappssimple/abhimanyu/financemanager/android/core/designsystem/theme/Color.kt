@file:Suppress("unused")

package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White

// Palette

// region Brown
internal val Brown1000 = Color(
    color = 0xFF6C2E14,
)
internal val Brown900 = Color(
    color = 0xFF7B3F25,
)
internal val Brown800 = Color(
    color = 0xFF8A5035,
)
internal val Brown700 = Color(
    color = 0xFF996144,
)
internal val Brown600 = Color(
    color = 0xFFA77254,
)
internal val Brown500 = Color(
    color = 0xFFB68264,
)
internal val Brown400 = Color(
    color = 0xFFC59374,
)
internal val Brown300 = Color(
    color = 0xFFD4A484,
)
internal val Brown200 = Color(
    color = 0xFFE2B595,
)
internal val Brown100 = Color(
    color = 0xFFF1C6A4,
)
internal val Brown50 = Color(
    color = 0xFFFFD7B5,
)
internal val Brown10 = Color(
    color = 0xFFFFF8F2,
)
// endregion

// region Blue
internal val Blue1000 = Color(
    color = 0xFF0346B2,
)
internal val Blue900 = Color(
    color = 0xFF1858BA,
)
internal val Blue800 = Color(
    color = 0xFF2D69C2,
)
internal val Blue700 = Color(
    color = 0xFF427BCA,
)
internal val Blue600 = Color(
    color = 0xFF578CD1,
)
internal val Blue500 = Color(
    color = 0xFF6D9ED9,
)
internal val Blue400 = Color(
    color = 0xFF81AFE1,
)
internal val Blue300 = Color(
    color = 0xFF97C0E8,
)
internal val Blue200 = Color(
    color = 0xFFACD2F0,
)
internal val Blue100 = Color(
    color = 0xFFC0E3F8,
)
internal val Blue50 = Color(
    color = 0xFFD5F4FF,
)
internal val Blue10 = Color(
    color = 0xFFEAF9FF,
)
// endregion

// region Green
internal val Green1000 = Color(
    color = 0xFF419243,
)
internal val Green900 = Color(
    color = 0xFF529E54,
)
internal val Green800 = Color(
    color = 0xFF63A864,
)
internal val Green700 = Color(
    color = 0xFF74B375,
)
internal val Green600 = Color(
    color = 0xFF85BE85,
)
internal val Green500 = Color(
    color = 0xFF96C996,
)
internal val Green400 = Color(
    color = 0xFFA7D4A6,
)
internal val Green300 = Color(
    color = 0xFFB8DFB6,
)
internal val Green200 = Color(
    color = 0xFFC9EAC7,
)
internal val Green100 = Color(
    color = 0xFFDAF5D7,
)
internal val Green50 = Color(
    color = 0xFFEAFFE7,
)
internal val Green10 = Color(
    color = 0xFFF4FFF2,
)
// endregion

internal val Error10 = Color(
    red = 65,
    green = 14,
    blue = 11,
)
internal val Error90 = Color(
    red = 249,
    green = 222,
    blue = 220,
)

internal val Gray50 = Color(
    color = 0xFFDDDDDD,
)
internal val Gray10 = Color(
    color = 0xFFEEEEEE,
)

// Theme colors

/**
 * Used.
 */
internal val Primary = Blue900
internal val OnPrimary = White
internal val PrimaryContainer = Blue50
internal val OnPrimaryContainer = DarkGray

internal val Secondary = Brown900
internal val OnSecondary = White
internal val SecondaryContainer = Brown50
internal val OnSecondaryContainer = Brown1000

internal val Tertiary = Green900
internal val OnTertiary = White
internal val TertiaryContainer = Green100
internal val OnTertiaryContainer = Green1000

/**
 * Usages.
 */
internal val Background = Blue10
internal val OnBackground = DarkGray

/**
 * Usages.
 */
internal val Surface = Blue10

/**
 * Usages - Bottom sheet scrim color.
 */
internal val OnSurface = DarkGray

internal val SurfaceVariant = LightGray
internal val OnSurfaceVariant = DarkGray

internal val InversePrimary = Blue900
internal val InverseSurface = DarkGray
internal val InverseOnSurface = White

/**
 * Usages.
 */
internal val Error = Red
internal val OnError = White
internal val ErrorContainer = Error90
internal val OnErrorContainer = Error10

internal val Outline = LightGray
