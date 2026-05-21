package com.example.ipv5

expect suspend fun fetchIp(v6: Boolean): String
expect suspend fun pingHost(host: String): Long?
expect suspend fun resolveDns(domain: String): String?
