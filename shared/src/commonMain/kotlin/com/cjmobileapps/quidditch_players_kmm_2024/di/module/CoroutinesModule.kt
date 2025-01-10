package com.cjmobileapps.quidditch_players_kmm_2024.di.module

import com.cjmobileapps.quidditch_players_kmm_2024.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.quidditch_players_kmm_2024.util.coroutine.CoroutineDispatchersImpl
import com.cjmobileapps.quidditch_players_kmm_2024.util.time.TimeUtilImpl
import com.cjmobileapps.quidditch_players_kmm_2024.util.time.TimeUtil
import org.koin.dsl.module

val coroutinesModule = module {
    single<CoroutineDispatchers> { CoroutineDispatchersImpl }
    single<TimeUtil> { TimeUtilImpl }
}
