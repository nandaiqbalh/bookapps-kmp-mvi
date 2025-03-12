package com.nandaiqbalh.kmp.bookapp.core.presentation

import androidx.compose.ui.graphics.Color

// Extension function to convert hexadecimal color string to Color object
fun String.toColor(): Color {
	require(startsWith("#") && (length == 7 || length == 9)) { "Invalid hex color format" }
	val colorInt = substring(1).toLong(16)
	return if (length == 7) {
		Color(
			red = (colorInt shr 16 and 0xFF) / 255f,
			green = (colorInt shr 8 and 0xFF) / 255f,
			blue = (colorInt and 0xFF) / 255f
		)
	} else {
		Color(
			red = (colorInt shr 16 and 0xFF) / 255f,
			green = (colorInt shr 8 and 0xFF) / 255f,
			blue = (colorInt and 0xFF) / 255f,
			alpha = (colorInt shr 24 and 0xFF) / 255f
		)
	}
}


val primaryBlue = "#2F4DD3".toColor()
val bgButtonSecondary = "#F2F5F8".toColor()
val pressedButton = "#001FA9".toColor()
val fontPrimary = "#252A3C".toColor()
val grayFont = "#BCC0D0".toColor()

val DarkBlue = Color(0xFF0B405E)
val DesertWhite = Color(0xFFF7F7F7)
val SandYellow = Color(0xFFFFBD64)
val LightBlue = Color(0xFF9AD9FF)