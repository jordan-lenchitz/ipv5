package net.notipv6.ipv5

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.Foundation.*
import platform.posix.*
import kotlinx.cinterop.*

@OptIn(ExperimentalForeignApi::class)
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

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
actual suspend fun resolveDns(domain: String): String? = withContext(Dispatchers.Default) {
    memScoped {
        val hints = alloc<addrinfo>()
        hints.ai_family = AF_UNSPEC
        hints.ai_socktype = SOCK_STREAM
        
        val result = allocPointerTo<addrinfo>()
        if (getaddrinfo(domain, null, hints.ptr, result.ptr) == 0) {
            val res = result.value!!
            val ipString = allocArray<ByteVar>(INET6_ADDRSTRLEN)
            val addr = res.ai_addr!!
            
            val ip = if (res.ai_family == AF_INET) {
                val addrIn = addr.reinterpret<sockaddr_in>()
                inet_ntop(AF_INET, addrIn.ptr.memberAt(0).ptr, ipString, INET_ADDRSTRLEN.convert())
            } else {
                val addrIn6 = addr.reinterpret<sockaddr_in6>()
                inet_ntop(AF_INET6, addrIn6.ptr.memberAt(0).ptr, ipString, INET6_ADDRSTRLEN.convert())
            }
            
            val resultIp = ip?.toKString()
            freeaddrinfo(res)
            resultIp
        } else {
            null
        }
    }
}

actual fun currentTimeMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()
