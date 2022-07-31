@file:Suppress("unused")

package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme

import androidx.compose.ui.graphics.Color

// Standard colors
internal val Red = Color.Red
//val Green = Color.Green
val Black = Color.Black
val White = Color.White
val Transparent = Color.Transparent
val LightGray = Color.LightGray
val DarkGray = Color.DarkGray
val Unspecified = Color.Unspecified


// Palette

internal val Neutral10 = Color(red = 28, green = 27, blue = 31)
internal val Neutral20 = Color(red = 49, green = 48, blue = 51)
internal val Neutral95 = Color(red = 244, green = 239, blue = 244)
internal val Neutral99 = Color(red = 255, green = 251, blue = 254)

//val NeutralVariant30 = Color(red = 73, green = 69, blue = 79)
internal val NeutralVariant50 = Color(red = 121, green = 116, blue = 126)
//val NeutralVariant90 = Color(red = 231, green = 224, blue = 236)

internal val Primary80 = Color(red = 208, green = 188, blue = 255)

internal val Error10 = Color(red = 65, green = 14, blue = 11)
//val Error40 = Color(red = 179, green = 38, blue = 30)
internal val Error90 = Color(red = 249, green = 222, blue = 220)

internal val Brown1000 = Color(0xFF6C2E14)
internal val Brown900 = Color(0xFF7B3F25)
//val Brown800 = Color(0xFF8A5035)
//val Brown700 = Color(0xFF996144)
//val Brown600 = Color(0xFFA77254)
//val Brown500 = Color(0xFFB68264)
//val Brown400 = Color(0xFFC59374)
//val Brown300 = Color(0xFFD4A484)
//val Brown200 = Color(0xFFE2B595)
internal val Brown100 = Color(0xFFF1C6A4)
//val Brown50 = Color(0xFFFFD7B5)
//val Brown10 = Color(0xFFFFF8F2)

internal val Blue1000 = Color(0xFF0346B2)
internal val Blue900 = Color(0xFF1858BA)
//val Blue800 = Color(0xFF2D69C2)
//val Blue700 = Color(0xFF427BCA)
//val Blue600 = Color(0xFF578CD1)
//val Blue500 = Color(0xFF6D9ED9)
//val Blue400 = Color(0xFF81AFE1)
//val Blue300 = Color(0xFF97C0E8)
//val Blue200 = Color(0xFFACD2F0)
val Blue100 = Color(0xFFC0E3F8)
val Blue50 = Color(0xFFD5F4FF)
internal val Blue10 = Color(0xFFEAF9FF)

internal val Green1000 = Color(0xFF419243)
internal val Green900 = Color(0xFF529E54)
//val Green800 = Color(0xFF63A864)
val Green700 = Color(0xFF74B375)
//val Green600 = Color(0xFF85BE85)
//val Green500 = Color(0xFF96C996)
//val Green400 = Color(0xFFA7D4A6)
//val Green300 = Color(0xFFB8DFB6)
//val Green200 = Color(0xFFC9EAC7)
internal val Green100 = Color(0xFFDAF5D7)
//val Green50 = Color(0xFFEAFFE7)
//val Green10 = Color(0xFFF4FFF2)

//val Gray50 = Color(0xFFDDDDDD)
//val Gray10 = Color(0xFFEEEEEE)


// Theme colors

// Material 2
internal val PrimaryVariant = Blue900
internal val SecondaryVariant = Blue900

// Material 3

/**
 * Used
 */
val Primary = Blue900
val OnPrimary = White
val PrimaryContainer = Blue100
val OnPrimaryContainer = Blue1000
val InversePrimary = Primary80

val Secondary = Brown900
val OnSecondary = White
val SecondaryContainer = Brown100
val OnSecondaryContainer = Brown1000

val Tertiary = Green900
val OnTertiary = White
val TertiaryContainer = Green100
val OnTertiaryContainer = Green1000

val Background = Neutral99
val OnBackground = Neutral10

/**
 * Used
 */
val Surface = Blue10

/**
 * Usages - Bottom sheet scrim color
 */
val OnSurface = Black
val SurfaceVariant = LightGray
val OnSurfaceVariant = DarkGray
val InverseSurface = Neutral20
val InverseOnSurface = Neutral95

/**
 * Used
 */
val Error = Red
val OnError = White
val ErrorContainer = Error90
val OnErrorContainer = Error10

val Outline = NeutralVariant50

