package net.notipv6.ipv5

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import kotlin.random.Random

object GlobalAppState {
    var ipv7Mode = mutableStateOf(false)
    
    val fonts = listOf(
        FontFamily.Default,
        FontFamily.Monospace,
        FontFamily.Cursive,
        FontFamily.Serif
    )
    
    fun getRandomFont() = fonts.random()
    
    fun getRandomVibrantColor(): Color {
        // generate thousands of potential colors
        // constraint: not white (255,255,255)
        // constraint: not too dark (sum of rgb > 300 to ensure visibility)
        while (true) {
            val r = Random.nextInt(0, 256)
            val g = Random.nextInt(0, 256)
            val b = Random.nextInt(0, 256)
            
            // exclude white
            if (r == 255 && g == 255 && b == 255) continue
            
            // ensure it's not too dark (perceived brightness)
            if (r + g + b > 300) {
                return Color(r, g, b)
            }
        }
    }
}
