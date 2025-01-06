package com.cjmobileapps.quidditch_players_kmm_2024.di.module

import com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers.QuidditchPlayersRepository
import com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers.QuidditchPlayersRepositoryImpl
import com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditch_players_kmm_2024.datasource.QuidditchPlayersApiDataSource
import com.cjmobileapps.quidditch_players_kmm_2024.datasource.QuidditchPlayersLocalDataSource
import com.cjmobileapps.quidditch_players_kmm_2024.room.QuidditchPlayersDao
import com.cjmobileapps.quidditch_players_kmm_2024.room.QuidditchPlayersDaoImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::QuidditchPlayersApiDataSource).bind<QuidditchPlayersApiDataSource>()
    singleOf(::QuidditchPlayersRepositoryImpl).bind<QuidditchPlayersRepository>()
    singleOf(::QuidditchPlayersUseCase).bind<QuidditchPlayersUseCase>()
    singleOf(::QuidditchPlayersDaoImpl).bind<QuidditchPlayersDao>()
    singleOf(::QuidditchPlayersLocalDataSource).bind<QuidditchPlayersLocalDataSource>()
}
