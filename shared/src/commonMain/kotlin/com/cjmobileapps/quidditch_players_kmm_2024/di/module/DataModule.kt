package com.cjmobileapps.quidditch_players_kmm_2024.di.module

import com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers.QuidditchPlayersRepository
import com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers.QuidditchPlayersRepositoryImpl
import com.cjmobileapps.quidditch_players_kmm_2024.network.QuidditchPlayersApiDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::QuidditchPlayersApiDataSource).bind<QuidditchPlayersApiDataSource>()
    singleOf(::QuidditchPlayersRepositoryImpl).bind<QuidditchPlayersRepository>()
}
