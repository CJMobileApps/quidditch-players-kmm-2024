package com.cjmobileapps.quidditch_players_kmm_2024.util.time

interface TimeUtil {
    suspend fun delayWithRandomTime()

    fun getRandomSeconds(): Long

    fun isDelayLoopRunning(): Boolean
}
