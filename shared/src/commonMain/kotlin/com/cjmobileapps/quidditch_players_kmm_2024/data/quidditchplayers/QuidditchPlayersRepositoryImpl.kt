package com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers

import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Player
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Position
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrapper
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrappers
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Status
import com.cjmobileapps.quidditch_players_kmm_2024.network.QuidditchPlayersApiDataSource
import com.cjmobileapps.quidditch_players_kmm_2024.util.coroutine.CoroutineDispatchers
import kotlinx.coroutines.flow.Flow

class QuidditchPlayersRepositoryImpl(
    val quidditchPlayersApiDataSource: QuidditchPlayersApiDataSource,
) : QuidditchPlayersRepository {

    override suspend fun getAllHouses() = quidditchPlayersApiDataSource.getAllHouses()

    override suspend fun getAllHousesFlow(): Flow<List<House>> {
        TODO("Not yet implemented")
    }

    override suspend fun createAllHousesToDB(houses: List<House>) {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayersByHouse(houseName: String): ResponseWrapper<List<Player>> {
        TODO("Not yet implemented")
    }

    override suspend fun createPlayersByHouseToDB(players: List<PlayerEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPlayersFlow(): Flow<List<PlayerEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPlayersAndPositions(houseName: String): ResponseWrappers<List<Player>, Map<Int, Position>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchStatusByHouseName(houseName: String): ResponseWrapper<Status> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchStatusByPlayerId(playerId: String): ResponseWrapper<Status> {
        TODO("Not yet implemented")
    }
}
