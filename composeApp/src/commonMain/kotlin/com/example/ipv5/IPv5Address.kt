package com.example.ipv5

import kotlin.random.Random

data class IPv5Address(
    val octets: IntArray = intArrayOf(0, 0, 0, 0, 0)
) {
    companion object {
        fun random(): IPv5Address {
            return IPv5Address(
                intArrayOf(
                    Random.nextInt(256),
                    Random.nextInt(256),
                    Random.nextInt(256),
                    Random.nextInt(256),
                    Random.nextInt(256)
                )
            )
        }
    }
}
