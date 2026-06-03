package net.notipv6.ipv5

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.Foundation.*
import platform.posix.*
import kotlinx.cinterop.*

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual suspend fun fetchIp(v6: Boolean): String = withContext(Dispatchers.Default) {
    try {
        val urlString = if (v6) "https://api64.ipify.org" else "https://api.ipify.org"
        val url = NSURL.URLWithString(urlString)
        val data = NSData.dataWithContentsOfURL(url!!)
        val ip = NSString.create(data = data!!, encoding = NSUTF8StringEncoding) as String
        
        if (v6 && !ip.contains(":")) {
            val parts = ip.split(".")
            if (parts.size == 4) {
                val hexParts = parts.map { it.toIntOrNull()?.toString(16)?.padStart(2, '0') ?: "00" }
                "2001:db8:85a3:8d3:1319:8a2e:${hexParts[0]}${hexParts[1]}:${hexParts[2]}${hexParts[3]} (Realn't)"
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

actual suspend fun pingHost(host: String): Long? {
    // Ping is complex on iOS without raw sockets or external libs.
    // Returning null for now.
    return null
}

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class, BetaInteropApi::class)
actual suspend fun resolveDns(domain: String): String? = withContext(Dispatchers.Default) {
    memScoped {
        val hints = alloc<addrinfo>()
        hints.ai_family = AF_UNSPEC
        hints.ai_socktype = SOCK_STREAM
        
        val result = allocPointerTo<addrinfo>()
        if (getaddrinfo(domain, null, hints.ptr, result.ptr) == 0) {
            val res = result.value!!
            val host = allocArray<ByteVar>(NI_MAXHOST)
            
            val resultIp = if (getnameinfo(res.pointed.ai_addr, res.pointed.ai_addrlen, host, NI_MAXHOST.toUInt(), null, 0u, NI_NUMERICHOST) == 0) {
                host.toKString()
            } else {
                null
            }
            
            freeaddrinfo(res)
            resultIp
        } else {
            null
        }
    }
}

actual fun currentTimeMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()

actual fun exitApp() {
    platform.posix.exit(0)
}
