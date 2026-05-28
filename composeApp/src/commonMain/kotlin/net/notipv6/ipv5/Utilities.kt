package net.notipv6.ipv5

import kotlin.random.Random

object IPv5Utilities {

    fun entangleMac(ipv5: IPv5Address, mac: String): String {
        val macBytes = mac.split(":").mapNotNull { it.toIntOrNull(16) }
        if (macBytes.isEmpty()) return "INVALID MAC"
        
        val entangledOctets = ipv5.octets.toMutableList()
        entangledOctets[0] = entangledOctets[0] xor (macBytes.getOrElse(0) { 0 })
        entangledOctets[1] = entangledOctets[1] xor (macBytes.getOrElse(1) { 0 })
        
        return entangledOctets.joinToString(":") { it.toString(16).uppercase().padStart(2, '0') }
    }

    fun predictPort(batteryPercentage: Int): Int {
        val timeFactor = (System.currentTimeMillis() % 1000).toInt()
        return (1024 + (batteryPercentage * 13) + timeFactor) % 65535
    }

    fun getBoomerangLatency(): Long {
        return Random.nextLong(1500, 5000) 
    }

    fun evaluateSimpleMath(input: String): Long? {
        val operators = listOf("+", "-", "*", "/")
        val operator = operators.find { input.contains(it) } ?: return null
        val parts = input.split(operator).map { it.trim().toLongOrNull() }
        if (parts.size != 2 || parts[0] == null || parts[1] == null) return null
        
        val a = parts[0]!!
        val b = parts[1]!!
        
        return when (operator) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> if (b != 0L) a / b else null
            else -> null
        }
    }

    fun getAstrologicalIp(sign: String): String {
        val seed = sign.hashCode().toLong()
        val random = Random(seed)
        return (1..5).joinToString(":") { random.nextInt(0, 256).toString(16).uppercase().padStart(2, '0') }
    }

    fun getEmojiDns(domain: String): String {
        val emojis = listOf("🌍", "💻", "🔥", "💀", "🚀", "⚡", "🦖", "🍔", "🦄", "🌈")
        val random = Random(domain.hashCode().toLong())
        return (1..5).map { emojis[random.nextInt(emojis.size)] }.joinToString(" ➡️ ")
    }

    fun scrambleMacCulinary(mac: String): String {
        val ingredients = listOf("Salt", "Pepper", "Onion", "Garlic", "Chicken", "Noodle", "Broth")
        val random = Random(mac.hashCode().toLong())
        val recipe = (1..3).map { ingredients[random.nextInt(ingredients.size)] }.joinToString("-")
        return "MAC-SOUP-" + recipe.uppercase()
    }

    fun getParanormalLatency(): String {
        val ghosts = listOf("Casper", "Slimer", "Beetlejuice", "Bloody Mary")
        val latency = Random.nextLong(666, 6666)
        return "${latency}ms (Ghost detected: ${ghosts.random()})"
    }

    fun getTeaLeafReading(): String {
        val fortunes = listOf(
            "A packet will find its home soon.",
            "Beware of the router with three antennas.",
            "A DNS resolution brings unexpected joy.",
            "You will encounter a stable IP in the near future."
        )
        return fortunes.random()
    }
}
