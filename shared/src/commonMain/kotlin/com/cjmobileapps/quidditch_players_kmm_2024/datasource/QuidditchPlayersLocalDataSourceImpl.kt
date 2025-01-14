package com.cjmobileapps.quidditch_players_kmm_2024.datasource

import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity
import com.cjmobileapps.quidditch_players_kmm_2024.room.QuidditchPlayersDao
import com.cjmobileapps.quidditch_players_kmm_2024.util.coroutine.CoroutineDispatchers
import kotlinx.coroutines.delay

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class QuidditchPlayersLocalDataSourceImpl(
    private val quidditchPlayersDao: QuidditchPlayersDao,
    private val coroutineDispatchers: CoroutineDispatchers,
) : QuidditchPlayersLocalDataSource {
    override suspend fun getAllHousesFlow(): Flow<List<House>> {
        return withContext(coroutineDispatchers.io) {
            flow {
                while (true) {
                    emit(quidditchPlayersDao.getAllHouses()) // Call the suspend function
                    delay(500) // Poll every 1 second
                }
            }
        }
    }

    override suspend fun createAllHouses(houses: List<House>) {
        withContext(coroutineDispatchers.io) {
            quidditchPlayersDao.deleteAllHouses()
            quidditchPlayersDao.insertAllHouses(houses)
        }
    }

    override suspend fun getAllPlayersFlow(): Flow<List<PlayerEntity>> {
        return withContext(coroutineDispatchers.io) {
            flow {
                while (true) {
                    emit(quidditchPlayersDao.getAllPlayers()) // Call the suspend function
                    delay(500) // Poll every 1 second
                }
            }
        }
    }

    override suspend fun createPlayersByHouseToDB(players: List<PlayerEntity>) {
        withContext(coroutineDispatchers.io) {
            quidditchPlayersDao.deleteAllPlayers()
            quidditchPlayersDao.insertAllPlayers(players)
        }
    }
}
