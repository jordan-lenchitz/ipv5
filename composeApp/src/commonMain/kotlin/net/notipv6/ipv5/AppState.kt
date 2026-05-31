package net.notipv6.ipv5

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import kotlin.random.Random

object GlobalAppState {
    var ipv7Mode = mutableStateOf(false)
    var accessibilityMode = mutableStateOf(false)
    
    // Persistent colors for the session
    val currentBgColor = mutableStateOf(getRandomVibrantColor())
    val currentNavColor = mutableStateOf(getRandomVibrantColor())
    val currentTextColor = mutableStateOf(getContrastingColor(currentBgColor.value))

    val fonts = listOf(
        FontFamily.Default,
        FontFamily.Monospace,
        FontFamily.Cursive,
        FontFamily.Serif
    )
    
    fun getRandomFont() = fonts.random()
    
    fun getContrastingColor(background: Color): Color {
        // Calculate perceived brightness
        val brightness = (background.red * 299 + background.green * 587 + background.blue * 114) / 1000
        // If background is light, use dark text; if dark, use light text
        return if (brightness > 0.5f) Color.Black else Color.White
    }

    fun getRandomVibrantColor(): Color {
        while (true) {
            val r = Random.nextFloat()
            val g = Random.nextFloat()
            val b = Random.nextFloat()
            
            // Perceived brightness check to avoid mid-range grays that are hard to contrast
            val brightness = (r * 299 + g * 587 + b * 114) / 1000
            
            // We want colors that are either definitively "light" or definitively "dark"
            // to allow for high contrast text. Also avoid pure white.
            if (brightness < 0.3f || brightness > 0.7f) {
                if (r > 0.9f && g > 0.9f && b > 0.9f) continue // Skip near-white
                return Color(r, g, b)
            }
        }
    }

    fun refreshColors() {
        val bg = getRandomVibrantColor()
        currentBgColor.value = bg
        currentNavColor.value = getRandomVibrantColor()
        currentTextColor.value = getContrastingColor(bg)
    }
}
