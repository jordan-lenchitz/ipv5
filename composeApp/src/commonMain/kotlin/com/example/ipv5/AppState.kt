package com.example.ipv5

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.font.FontFamily

object GlobalAppState {
    var ipv7Mode = mutableStateOf(false)
    
    val fonts = listOf(
        FontFamily.Default,
        FontFamily.Monospace,
        FontFamily.Cursive,
        FontFamily.Serif
    )
    
    val vibrantColors = listOf(
        Color(0xFFFF00FF), // Magenta
        Color(0xFF00FFFF), // Cyan
        Color(0xFFFFFF00), // Yellow
        Color(0xFFFF4500), // OrangeRed
        Color(0xFF00FF00), // Lime
        Color(0xFF7B68EE), // MediumSlateBlue
        Color(0xFFFF1493)  // DeepPink
    )
    
    fun getRandomFont() = fonts.random()
    fun getRandomVibrantColor() = vibrantColors.random()
}
