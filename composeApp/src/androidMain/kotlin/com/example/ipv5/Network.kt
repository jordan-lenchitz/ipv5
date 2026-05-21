package com.example.ipv5

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

actual suspend fun fetchIp(v6: Boolean): String = withContext(Dispatchers.IO) {
    try {
        val url = if (v6) "https://api64.ipify.org" else "https://api.ipify.org"
        val ip = URL(url).readText()
        if (v6 && !ip.contains(":")) {
            "Not Found (Your ISP is weak)"
        } else {
            ip
        }
    } catch (e: Exception) {
        "Unavailable"
    }
}
