package com.cjmobileapps.quidditch_players_kmm_2024.datasource

import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity
import kotlinx.coroutines.flow.Flow

interface QuidditchPlayersLocalDataSource {
    suspend fun getAllHousesFlow(): Flow<List<House>>

    suspend fun createAllHouses(houses: List<House>)

    suspend fun getAllPlayersFlow(): Flow<List<PlayerEntity>>

    suspend fun createPlayersByHouseToDB(players: List<PlayerEntity>)
}
