package com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers

import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Player
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Position
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrapper
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrappers
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Status
import kotlinx.coroutines.flow.Flow

interface QuidditchPlayersRepository {
    suspend fun getAllHouses(): ResponseWrapper<List<House>>

    suspend fun getAllHousesFlow(): Flow<List<House>>

    suspend fun createAllHousesToDB(houses: List<House>)

    suspend fun getPlayersByHouse(houseName: String): ResponseWrapper<List<Player>>

    suspend fun createPlayersByHouseToDB(players: List<PlayerEntity>)

    suspend fun getAllPlayersFlow(): Flow<List<PlayerEntity>>

    suspend fun fetchPlayersAndPositions(houseName: String): ResponseWrappers<List<Player>, Map<Int, Position>>

    suspend fun fetchStatusByHouseName(houseName: String): ResponseWrapper<Status>

    suspend fun fetchStatusByPlayerId(playerId: String): ResponseWrapper<Status>
}
