package com.cjmobileapps.quidditch_players_kmm_2024.room

import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity

class QuidditchPlayersDaoImpl(quidditchPlayersDatabase: QuidditchPlayersDatabase): QuidditchPlayersDao {

    private var quidditchPlayersDao : QuidditchPlayersDao = quidditchPlayersDatabase.quidditchPlayersDao()

    override fun getAllHouses() = quidditchPlayersDao.getAllHouses()

    override fun insertAllHouses(houses: List<House>) = quidditchPlayersDao.insertAllHouses(houses)

    override fun deleteAllHouses() = quidditchPlayersDao.deleteAllHouses()

    override fun getAllPlayers() = quidditchPlayersDao.getAllPlayers()

    override fun insertAllPlayers(players: List<PlayerEntity>) = quidditchPlayersDao.insertAllPlayers(players)

    override fun deleteAllPlayers() = quidditchPlayersDao.deleteAllPlayers()
}
