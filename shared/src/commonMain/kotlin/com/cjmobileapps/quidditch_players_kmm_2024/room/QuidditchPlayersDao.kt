package com.cjmobileapps.quidditch_players_kmm_2024.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuidditchPlayersDao {
    @Query("SELECT * FROM house")
    fun getAllHouses(): Flow<List<House>>

    @Insert
    fun insertAllHouses(houses: List<House>)

    @Query("DELETE FROM house")
    fun deleteAllHouses()

    @Query("SELECT * FROM playerEntity")
    fun getAllPlayers(): Flow<List<PlayerEntity>>

    @Insert
    fun insertAllPlayers(players: List<PlayerEntity>)

    @Query("DELETE FROM PlayerEntity")
    fun deleteAllPlayers()
}
