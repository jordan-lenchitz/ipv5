package com.example.ipv5

import kotlin.math.absoluteValue
import kotlin.math.sin
import kotlin.random.Random

data class IPv5Address(
    val octets: IntArray = intArrayOf(0, 0, 0, 0, 0),
    val fluxCapacitorState: Double = 0.0,
    val quantumEntanglementIndex: Int = 0
) {
    override fun toString(): String {
        return octets.joinToString(".") + " [Flux: ${fluxCapacitorState.toString().take(5)}V, QEI: $quantumEntanglementIndex]"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as IPv5Address

        if (!octets.contentEquals(other.octets)) return false
        if (fluxCapacitorState != other.fluxCapacitorState) return false
        if (quantumEntanglementIndex != other.quantumEntanglementIndex) return false

        return true
    }

    override fun hashCode(): Int {
        var result = octets.contentHashCode()
        result = 31 * result + fluxCapacitorState.hashCode()
        result = 31 * result + quantumEntanglementIndex
        return result
    }

    companion object {
        fun random(): IPv5Address {
            return generateQuantum()
        }

        fun generateQuantum(): IPv5Address {
            val atmosphericNoise = Random.nextDouble()
            val phaseShift = sin(atmosphericNoise * 10.0)
            
            val o1 = (Random.nextInt(256) xor (phaseShift * 100).toInt().absoluteValue) % 256
            val o2 = (Random.nextInt(256) + 42) % 256
            val o3 = Random.nextInt(256)
            val o4 = (o1 + o2) % 256
            val o5 = Random.nextInt(256)
            
            return IPv5Address(
                intArrayOf(o1, o2, o3, o4, o5),
                fluxCapacitorState = phaseShift * 1.21, // 1.21 Gigawatts
                quantumEntanglementIndex = Random.nextInt(1000, 9999)
            )
        }
    }
}
