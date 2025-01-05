package com.cjmobileapps.quidditch_players_kmm_2024.util.coroutine

import kotlin.coroutines.CoroutineContext

interface CoroutineDispatchers {
    val io: CoroutineContext
    val main: CoroutineContext
}
