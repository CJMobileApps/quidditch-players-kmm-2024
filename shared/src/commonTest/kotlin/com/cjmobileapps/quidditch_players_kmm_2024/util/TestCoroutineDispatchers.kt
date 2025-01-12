package com.cjmobileapps.quidditch_players_kmm_2024.util

import com.cjmobileapps.quidditch_players_kmm_2024.util.coroutine.CoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object TestCoroutineDispatchers : CoroutineDispatchers {
    override val io: CoroutineContext = Dispatchers.Unconfined

    override val main: CoroutineContext = Dispatchers.Unconfined
}
