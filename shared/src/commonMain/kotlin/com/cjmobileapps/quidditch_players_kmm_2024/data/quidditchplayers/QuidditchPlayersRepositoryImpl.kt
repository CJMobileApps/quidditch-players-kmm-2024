package com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers

import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity
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

    override suspend fun getPlayersByHouse(houseName: String) =
        quidditchPlayersApiDataSource.getPlayersByHouse(houseName)

    override suspend fun createPlayersByHouseToDB(players: List<PlayerEntity>) =
        quidditchPlayersLocalDataSource.createPlayersByHouseToDB(players)

    override suspend fun getAllPlayersFlow(): Flow<List<PlayerEntity>> =
        quidditchPlayersLocalDataSource.getAllPlayersFlow()

    override suspend fun fetchPlayersAndPositions(houseName: String) =
        quidditchPlayersApiDataSource.fetchPlayersAndPositions(houseName)

    override suspend fun fetchStatusByHouseName(houseName: String) =
        quidditchPlayersApiDataSource.getStatusByHouseName(houseName)

    override suspend fun fetchStatusByPlayerId(playerId: String) =
        quidditchPlayersApiDataSource.fetchStatusByPlayerId(playerId)
}
