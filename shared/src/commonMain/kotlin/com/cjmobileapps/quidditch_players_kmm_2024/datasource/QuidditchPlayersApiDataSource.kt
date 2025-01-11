package com.cjmobileapps.quidditch_players_kmm_2024.datasource

import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Player
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Position
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrapper
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrappers
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Status
import kotlinx.coroutines.Deferred

interface QuidditchPlayersApiDataSource {

    suspend fun getAllHouses(): ResponseWrapper<List<House>>

    suspend fun getPlayersByHouse(houseName: String): ResponseWrapper<List<Player>>

    suspend fun getPlayersByHouseDeferred(houseName: String): Deferred<ResponseWrapper<List<Player>>>

    suspend fun fetchPlayersAndPositions(houseName: String): ResponseWrappers<List<Player>, Map<Int, Position>>

    suspend fun getPositionsDeferred(): Deferred<ResponseWrapper<Map<Int, Position>>>

    suspend fun getStatusByHouseName(houseName: String): ResponseWrapper<Status>

    suspend fun fetchStatusByPlayerId(playerId: String): ResponseWrapper<Status>
}
