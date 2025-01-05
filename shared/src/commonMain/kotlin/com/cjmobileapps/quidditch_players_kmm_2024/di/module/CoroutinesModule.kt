package com.cjmobileapps.quidditch_players_kmm_2024.di.module

import com.cjmobileapps.quidditch_players_kmm_2024.network.QuidditchPlayersApiDataSource
import com.cjmobileapps.quidditch_players_kmm_2024.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.quidditch_players_kmm_2024.util.coroutine.CoroutineDispatchersImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coroutinesModule = module {
    singleOf(::QuidditchPlayersApiDataSource).bind<QuidditchPlayersApiDataSource>()
    single<CoroutineDispatchers> { CoroutineDispatchersImpl }
}
