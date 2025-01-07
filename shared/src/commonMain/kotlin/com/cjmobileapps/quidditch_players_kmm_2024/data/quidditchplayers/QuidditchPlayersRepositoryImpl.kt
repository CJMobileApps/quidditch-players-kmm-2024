package com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers

import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Player
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Position
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrapper
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrappers
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Status
import com.cjmobileapps.quidditch_players_kmm_2024.datasource.QuidditchPlayersApiDataSource
import com.cjmobileapps.quidditch_players_kmm_2024.datasource.QuidditchPlayersLocalDataSource
import kotlinx.coroutines.flow.Flow

class QuidditchPlayersRepositoryImpl(
    private val quidditchPlayersApiDataSource: QuidditchPlayersApiDataSource,
    private val quidditchPlayersLocalDataSource: QuidditchPlayersLocalDataSource,
) : QuidditchPlayersRepository {

    override suspend fun getAllHouses() = quidditchPlayersApiDataSource.getAllHouses()

    override suspend fun getAllHousesFlow(): Flow<List<House>> =
        quidditchPlayersLocalDataSource.getAllHousesFlow()

    override suspend fun createAllHousesToDB(houses: List<House>) =
        quidditchPlayersLocalDataSource.createAllHouses(houses)

    override suspend fun getPlayersByHouse(houseName: String): ResponseWrapper<List<Player>> {
        TODO("Not yet implemented")
    }

    override suspend fun createPlayersByHouseToDB(players: List<PlayerEntity>) =
        quidditchPlayersLocalDataSource.createPlayersByHouseToDB(players)

    override suspend fun getAllPlayersFlow(): Flow<List<PlayerEntity>> =
        quidditchPlayersLocalDataSource.getAllPlayersFlow()

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
