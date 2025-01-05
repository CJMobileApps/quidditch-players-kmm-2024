package com.cjmobileapps.quidditch_players_kmm_2024.util.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

object CoroutineDispatchersImpl : CoroutineDispatchers {
    override val io = Dispatchers.IO
    override val main = Dispatchers.Main
}
