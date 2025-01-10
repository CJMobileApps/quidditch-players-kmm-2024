package com.cjmobileapps.quidditch_players_kmm_2024.datasource

import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Player
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Position
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrapper
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrappers
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Status
import com.cjmobileapps.quidditch_players_kmm_2024.network.KtorHttpClient
import com.cjmobileapps.quidditch_players_kmm_2024.util.withContextApiWrapper
import com.cjmobileapps.quidditch_players_kmm_2024.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.quidditch_players_kmm_2024.util.withContextApiWrappers
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class QuidditchPlayersApiDataSource(
    private val ktorHttpClient: KtorHttpClient,
    private val coroutineDispatchers: CoroutineDispatchers,
) {

    suspend fun getAllHouses(): ResponseWrapper<List<House>> {
        return withContextApiWrapper(coroutineContext = coroutineDispatchers.io) {
            coroutineScope {
                async {
                    ktorHttpClient
                        .httpClient
                        .get("api/v1/quidditchplayers/house")
                        .body<ResponseWrapper<List<House>>>()
                }
            }
        }
    }

    suspend fun getPlayersByHouse(houseName: String): ResponseWrapper<List<Player>> {
        return withContextApiWrapper(coroutineContext = coroutineDispatchers.io) {
            getPlayersByHouseDeferred(houseName = houseName)
        }
    }

    private suspend fun getPlayersByHouseDeferred(houseName: String): Deferred<ResponseWrapper<List<Player>>> {
        return coroutineScope {
            async {
                ktorHttpClient
                    .httpClient
                    .get("api/v1/quidditchplayers/player") {
                        parameter("houseName", houseName)
                    }
                    .body<ResponseWrapper<List<Player>>>()
            }
        }
    }

    suspend fun fetchPlayersAndPositions(houseName: String): ResponseWrappers<List<Player>, Map<Int, Position>> {
        return withContextApiWrappers(
            coroutineContext = coroutineDispatchers.io,
            requestFunc1 = { getPlayersByHouseDeferred(houseName) },
            requestFunc2 = { getPositionsDeferred() },
        )
    }

    private suspend fun getPositionsDeferred(): Deferred<ResponseWrapper<Map<Int, Position>>> {
        return coroutineScope {
            async {
                ktorHttpClient
                    .httpClient
                    .get("api/v1/quidditchplayers/position")
                    .body<ResponseWrapper<Map<Int, Position>>>()
            }
        }
    }

    suspend fun getStatusByHouseName(houseName: String): ResponseWrapper<Status> {
        return withContextApiWrapper(coroutineContext = coroutineDispatchers.io) {
            coroutineScope {
                async {
                    ktorHttpClient
                        .httpClient
                        .get("api/v1/quidditchplayers/player/status") {
                            parameter("houseName", houseName)
                        }
                        .body<ResponseWrapper<Status>>()
                }
            }
        }
    }

    suspend fun fetchStatusByPlayerId(playerId: String): ResponseWrapper<Status> {
        return withContextApiWrapper(coroutineContext = coroutineDispatchers.io) {
            coroutineScope {
                async {
                    ktorHttpClient
                        .httpClient
                        .get {
                            url {
                                path("api/v1/quidditchplayers/player/status", playerId)
                            }
                        }
                        .body<ResponseWrapper<Status>>()
                }
            }
        }
    }
}
