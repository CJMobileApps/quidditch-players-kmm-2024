package com.cjmobileapps.quidditch_players_kmm_2024.util

import com.cjmobileapps.quidditch_players_kmm_2024.util.time.TimeUtil

object TestTimeUtil : TimeUtil {
    private var count = 1

    fun resetTestTimeUtil() {
        count = 1
    }

    override suspend fun delayWithRandomTime() { }

    override fun getRandomSeconds(): Long = 0

    override fun isDelayLoopRunning(): Boolean = (count-- > 0)
}
