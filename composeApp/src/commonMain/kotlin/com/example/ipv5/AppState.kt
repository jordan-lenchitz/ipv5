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
    
    fun getRandomFont() = fonts.random()
}
