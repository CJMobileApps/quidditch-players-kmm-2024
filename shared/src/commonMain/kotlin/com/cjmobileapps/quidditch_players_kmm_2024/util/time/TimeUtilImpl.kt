package com.cjmobileapps.quidditch_players_kmm_2024.util.time

import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

object TimeUtilImpl : TimeUtil {
    override fun isDelayLoopRunning() = true

    override suspend fun delayWithRandomTime() = delay(getRandomSeconds())

    override fun getRandomSeconds(): Long {
        val seconds = (1..60).random().toLong()
        return seconds.seconds.inWholeMilliseconds
    }
}
