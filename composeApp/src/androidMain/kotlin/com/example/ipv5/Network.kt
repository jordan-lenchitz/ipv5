package com.example.ipv5

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

actual suspend fun fetchIp(v6: Boolean): String = withContext(Dispatchers.IO) {
    try {
        val url = if (v6) "https://api64.ipify.org" else "https://api.ipify.org"
        val ip = URL(url).readText()
        if (v6 && !ip.contains(":")) {
            // Fake IPv6 from IPv4!
            val parts = ip.split(".")
            if (parts.size == 4) {
                val hexParts = parts.map { it.toIntOrNull()?.toString(16)?.padStart(2, '0') ?: "00" }
                "2001:db8:85a3:8d3:1319:8a2e:${hexParts[0]}${hexParts[1]}:${hexParts[2]}${hexParts[3]} (Fake)"
            } else {
                "Not Found (Your ISP is weak)"
            }
        } else {
            ip
        }
    } catch (e: Exception) {
        "Unavailable"
    }
}
