package net.notipv6.ipv5

import androidx.compose.runtime.Composable

@Composable
fun WordSearchPanel() {
    ChaoticPanel(title = "Word Search") {
        androidx.compose.material.Text(
            "COMING SOON", 
            style = androidx.compose.material.MaterialTheme.typography.h2,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
    }
}
