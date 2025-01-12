package com.cjmobileapps.quidditch_players_kmm_2024.data.datasource

import com.cjmobileapps.quidditch_players_kmm_2024.data.MockData
import com.cjmobileapps.quidditch_players_kmm_2024.datasource.QuidditchPlayersApiDataSourceImpl
import com.cjmobileapps.quidditch_players_kmm_2024.util.TestCoroutineDispatchers
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class QuidditchPlayersApiDataSourceImplTest {
    private lateinit var quidditchPlayersApiDataSource: QuidditchPlayersApiDataSourceImpl

    val mockHttpClient = HttpClient(MockEngine) {
        install(ContentNegotiation) {
            json(Json)  // Install ContentNegotiation with JSON support
        }
        engine {
            addHandler { request ->
                // Check that the request URL matches the expected URL
                if (request.url.toString() == "http://localhost/api/v1/quidditchplayers/house") {
                    return@addHandler respond(
                        content = ByteReadChannel(MockData.mockHousesResponseWrapperJson),
                        status = HttpStatusCode.OK,
                        headers = headersOf("Content-Type", "application/json")
                    )
                } else {
                    // Handle other requests with a 404 or some other response
                    return@addHandler respondError(HttpStatusCode.NotFound)
                }
            }
        }
    }


    private fun setupQuidditchPlayersApiDataSource() {
        quidditchPlayersApiDataSource =
            QuidditchPlayersApiDataSourceImpl(
                httpClient = mockHttpClient,
                coroutineDispatchers = TestCoroutineDispatchers,
            )
    }

    @Test
    fun `houses happy success flow`() =
        runTest {
            setupQuidditchPlayersApiDataSource()
            val houses = quidditchPlayersApiDataSource.getAllHouses()

            // verify
            assertEquals(
                MockData.mockHousesResponseWrapper,
                houses,
            )
        }

//    @Test
//    fun `get players by house success flow`() =
//        runTest {
//            // when
//            Mockito.`when`(mockQuidditchPlayersApi.getPlayersByHouse(houseName = HouseName.RAVENCLAW.name))
//                .thenReturn(
//                    MockData.mockRavenclawPlayersDeferredResponseSuccess,
//                )
//
//            // then
//            setupQuidditchPlayersApiDataSource()
//            val players = quidditchPlayersApiDataSource.getPlayersByHouse(HouseName.RAVENCLAW.name)
//
//            // verify
//            Assertions.assertEquals(
//                MockData.mockRavenclawPlayersResponseWrapper,
//                players,
//            )
//        }
//
//    @Test
//    fun`fetch players and positions success flow`() =
//        runTest {
//            // when
//            Mockito.`when`(mockQuidditchPlayersApi.getPlayersByHouse(houseName = HouseName.RAVENCLAW.name))
//                .thenReturn(
//                    MockData.mockRavenclawPlayersDeferredResponseSuccess,
//                )
//            Mockito.`when`(mockQuidditchPlayersApi.getPositions()).thenReturn(MockData.mockPositionsDeferredResponseSuccess)
//
//            // then
//            setupQuidditchPlayersApiDataSource()
//            val playersAndPositions = quidditchPlayersApiDataSource.fetchPlayersAndPositions(HouseName.RAVENCLAW.name)
//
//            // verify
//            Assertions.assertEquals(
//                MockData.mockRavenclawPlayersAndPositionsResponseWrappers,
//                playersAndPositions,
//            )
//        }
//
//    @Test
//    fun `fetch status by house name success flow`() =
//        runTest {
//            // when
//            Mockito.`when`(
//                mockQuidditchPlayersApi.getStatusByHouseName(houseName = HouseName.RAVENCLAW.name),
//            ).thenReturn(MockData.mockStatusDeferredResponseSuccess)
//
//            // then
//            setupQuidditchPlayersApiDataSource()
//            val status = quidditchPlayersApiDataSource.fetchStatusByHouseName(houseName = HouseName.RAVENCLAW.name)
//
//            // verify
//            Assertions.assertEquals(
//                MockData.mockStatusResponseWrapper,
//                status,
//            )
//        }
//
//    @Test
//    fun `fetch status by player id success flow`() =
//        runTest {
//            // given
//            val playerId = MockData.ravenclawTeam().first().id.toString()
//
//            // when
//            Mockito.`when`(
//                mockQuidditchPlayersApi.fetchStatusByPlayerId(playerId = playerId),
//            ).thenReturn(MockData.mockStatusDeferredResponseSuccess)
//
//            // then
//            setupQuidditchPlayersApiDataSource()
//            val status = quidditchPlayersApiDataSource.fetchStatusByPlayerId(playerId = playerId)
//
//            // verify
//            Assertions.assertEquals(
//                MockData.mockStatusResponseWrapper,
//                status,
//            )
//        }
}