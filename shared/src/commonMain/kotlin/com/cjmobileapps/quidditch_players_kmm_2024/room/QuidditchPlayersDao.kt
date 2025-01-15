package com.cjmobileapps.quidditch_players_kmm_2024.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity

@Dao
interface QuidditchPlayersDao {
    @Query("SELECT * FROM house")
    suspend fun getAllHouses(): List<House>

    @Insert
    suspend fun insertAllHouses(houses: List<House>)

    @Query("DELETE FROM house")
    suspend fun deleteAllHouses()

    @Query("SELECT * FROM playerEntity")
    suspend fun getAllPlayers(): List<PlayerEntity>

    @Insert
    suspend fun insertAllPlayers(players: List<PlayerEntity>)

    @Query("DELETE FROM PlayerEntity")
    suspend fun deleteAllPlayers()
}
