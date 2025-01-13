package com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers

import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerState
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrapper
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Status

interface QuidditchPlayersUseCase {
    suspend fun getHousesFromDB(onHousesResponse: (ResponseWrapper<List<House>>) -> Unit)

    suspend fun fetchHousesApi(): ResponseWrapper<Boolean>

    suspend fun fetchPlayersAndPositionsApis(houseName: String): ResponseWrapper<Boolean>

    suspend fun getAllPlayersToDB(onPlayersResponse: (ResponseWrapper<List<PlayerEntity>>) -> Unit)

    suspend fun fetchStatusByHouseName(houseName: String): ResponseWrapper<Status>

    suspend fun fetchStatusByPlayerId(playerId: String): ResponseWrapper<Status>

    fun getCurrentPlayer(): PlayerState?

    fun setCurrentPlayer(player: PlayerState)
}
