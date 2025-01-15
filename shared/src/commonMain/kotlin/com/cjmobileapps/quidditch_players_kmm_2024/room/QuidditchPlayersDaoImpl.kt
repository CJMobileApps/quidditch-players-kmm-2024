package com.cjmobileapps.quidditch_players_kmm_2024.room

import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity

class QuidditchPlayersDaoImpl(quidditchPlayersDatabase: QuidditchPlayersDatabase): QuidditchPlayersDao {

    private var quidditchPlayersDao : QuidditchPlayersDao = quidditchPlayersDatabase.quidditchPlayersDao()

    override suspend fun getAllHouses() = quidditchPlayersDao.getAllHouses()

    override suspend fun insertAllHouses(houses: List<House>) = quidditchPlayersDao.insertAllHouses(houses)

    override suspend fun deleteAllHouses() = quidditchPlayersDao.deleteAllHouses()

    override suspend fun getAllPlayers() = quidditchPlayersDao.getAllPlayers()

    override suspend fun insertAllPlayers(players: List<PlayerEntity>) = quidditchPlayersDao.insertAllPlayers(players)

    override suspend fun deleteAllPlayers() = quidditchPlayersDao.deleteAllPlayers()
}
