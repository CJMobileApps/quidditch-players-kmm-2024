package com.cjmobileapps.quidditch_players_kmm_2024.datasource

import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity
import com.cjmobileapps.quidditch_players_kmm_2024.room.QuidditchPlayersDao
import com.cjmobileapps.quidditch_players_kmm_2024.util.coroutine.CoroutineDispatchers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class QuidditchPlayersLocalDataSource(
    private val quidditchPlayersDao: QuidditchPlayersDao,
    private val coroutineDispatchers: CoroutineDispatchers,
) {
    suspend fun getAllHousesFlow(): Flow<List<House>> {
        return withContext(coroutineDispatchers.io) {
            quidditchPlayersDao.getAllHouses()
        }
    }

    suspend fun createAllHouses(houses: List<House>) {
        withContext(coroutineDispatchers.io) {
            quidditchPlayersDao.deleteAllHouses()
            quidditchPlayersDao.insertAllHouses(houses)
        }
    }

    suspend fun getAllPlayersFlow(): Flow<List<PlayerEntity>> {
        return withContext(coroutineDispatchers.io) {
            quidditchPlayersDao.getAllPlayers()
        }
    }

    suspend fun createPlayersByHouseToDB(players: List<PlayerEntity>) {
        withContext(coroutineDispatchers.io) {
            quidditchPlayersDao.deleteAllPlayers()
            quidditchPlayersDao.insertAllPlayers(players)
        }
    }
}
