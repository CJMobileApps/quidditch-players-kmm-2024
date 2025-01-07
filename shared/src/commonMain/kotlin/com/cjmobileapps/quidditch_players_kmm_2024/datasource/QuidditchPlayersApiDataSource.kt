package com.cjmobileapps.quidditch_players_kmm_2024.datasource

import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrapper
import com.cjmobileapps.quidditch_players_kmm_2024.network.KtorHttpClient
import com.cjmobileapps.quidditch_players_kmm_2024.util.withContextApiWrapper
import com.cjmobileapps.quidditch_players_kmm_2024.util.coroutine.CoroutineDispatchers
import io.ktor.client.call.body
import io.ktor.client.request.get
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
}
