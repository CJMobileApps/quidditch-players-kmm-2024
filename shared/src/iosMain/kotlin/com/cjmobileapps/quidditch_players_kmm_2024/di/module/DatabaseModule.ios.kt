package com.cjmobileapps.quidditch_players_kmm_2024.di.module

import com.cjmobileapps.quidditch_players_kmm_2024.room.DatabaseFactory
import com.cjmobileapps.quidditch_players_kmm_2024.room.QuidditchPlayersDatabase
import org.koin.dsl.module

actual val databaseModule = module {
    single<QuidditchPlayersDatabase> { DatabaseFactory.getDB()  }
}
