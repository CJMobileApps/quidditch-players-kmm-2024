package com.cjmobileapps.quidditch_players_kmm_2024.data.datasource

import com.cjmobileapps.quidditch_players_kmm_2024.data.MockData
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.HouseName
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

    lateinit var mockHttpClient: HttpClient


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
            // when
            mockHttpClient = HttpClient(MockEngine) {
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

            // then
            setupQuidditchPlayersApiDataSource()
            val houses = quidditchPlayersApiDataSource.getAllHouses()

            // verify
            assertEquals(
                MockData.mockHousesResponseWrapper,
                houses,
            )
        }

    @Test
    fun `get players by house success flow`() =
        runTest {
            // when
            mockHttpClient = HttpClient(MockEngine) {
                install(ContentNegotiation) {
                    json(Json)  // Install ContentNegotiation with JSON support
                }
                engine {
                    addHandler { request ->
                        // Check that the request URL matches the expected URL
                        if (request.url.toString() == "http://localhost/api/v1/quidditchplayers/player?houseName=RAVENCLAW") {
                            return@addHandler respond(
                                content = ByteReadChannel(MockData.mockRavenclawPlayersResponseWrapperJson),
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

            // then
            setupQuidditchPlayersApiDataSource()
            val players = quidditchPlayersApiDataSource.getPlayersByHouse(HouseName.RAVENCLAW.name)

            // verify
            assertEquals(
                MockData.mockRavenclawPlayersResponseWrapper,
                players,
            )
        }

    @Test
    fun`fetch players and positions success flow`() =
        runTest {
            // when
            mockHttpClient = HttpClient(MockEngine) {
                install(ContentNegotiation) {
                    json(Json)  // Install ContentNegotiation with JSON support
                }
                engine {
                    addHandler { request ->
                        // Check that the request URL matches the expected URL
                        if (request.url.toString() == "http://localhost/api/v1/quidditchplayers/player?houseName=RAVENCLAW") {
                            return@addHandler respond(
                                content = ByteReadChannel(MockData.mockRavenclawPlayersResponseWrapperJson),
                                status = HttpStatusCode.OK,
                                headers = headersOf("Content-Type", "application/json")
                            )
                        } else if (request.url.toString() == "http://localhost/api/v1/quidditchplayers/position") {
                            return@addHandler respond(
                                content = ByteReadChannel(MockData.mockPositionsResponseWrapperJson),
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

            // then
            setupQuidditchPlayersApiDataSource()
            val playersAndPositions = quidditchPlayersApiDataSource.fetchPlayersAndPositions(HouseName.RAVENCLAW.name)

            // verify
            assertEquals(
                MockData.mockRavenclawPlayersAndPositionsResponseWrappers,
                playersAndPositions,
            )
        }

    @Test
    fun `fetch status by house name success flow`() =
        runTest {
            // when
            mockHttpClient = HttpClient(MockEngine) {
                install(ContentNegotiation) {
                    json(Json)  // Install ContentNegotiation with JSON support
                }
                engine {
                    addHandler { request ->
                        // Check that the request URL matches the expected URL
                        if (request.url.toString() == "http://localhost/api/v1/quidditchplayers/player/status?houseName=RAVENCLAW") {
                            return@addHandler respond(
                                content = ByteReadChannel(MockData.mockStatusResponseWrapperJson),
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

            // then
            setupQuidditchPlayersApiDataSource()
            val status = quidditchPlayersApiDataSource.getStatusByHouseName(houseName = HouseName.RAVENCLAW.name)

            // verify
            assertEquals(
                MockData.mockStatusResponseWrapper,
                status,
            )
        }

    @Test
    fun `fetch status by player id success flow`() =
        runTest {
            // given
            val playerId = MockData.ravenclawTeam().first().id

            // when
            mockHttpClient = HttpClient(MockEngine) {
                install(ContentNegotiation) {
                    json(Json)  // Install ContentNegotiation with JSON support
                }
                engine {
                    addHandler { request ->
                        // Check that the request URL matches the expected URL
                        if (request.url.toString() == "http://localhost/api/v1/quidditchplayers/player/status/${playerId}") {
                            return@addHandler respond(
                                content = ByteReadChannel(MockData.mockStatusResponseWrapperJson),
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

            // then
            setupQuidditchPlayersApiDataSource()
            val status = quidditchPlayersApiDataSource.fetchStatusByPlayerId(playerId = playerId)

            // verify
            assertEquals(
                MockData.mockStatusResponseWrapper,
                status,
            )
        }
}
