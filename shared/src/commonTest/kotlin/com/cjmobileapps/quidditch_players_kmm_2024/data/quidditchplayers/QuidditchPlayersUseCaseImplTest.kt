package com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers

import com.cjmobileapps.quidditch_players_kmm_2024.data.MockData
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.HouseName
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrapper
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.toPlayerEntity
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.toPlayerState
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.toPlayersEntities
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode.Companion.atMost
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class QuidditchPlayersUseCaseImplTest {
    private lateinit var quidditchPlayersUseCase: QuidditchPlayersUseCaseImpl

    private val mockQuidditchPlayersRepository = mock<QuidditchPlayersRepository>()

    private fun setupQuidditchPlayersUseCase() {
        quidditchPlayersUseCase =
            QuidditchPlayersUseCaseImpl(
                quidditchPlayersRepository = mockQuidditchPlayersRepository,
            )
    }

    @Test
    fun `getHousesFromDB happy success flow`() =
        runTest {
            // given
            val mockGetAllHousesFlow: Flow<List<House>> =
                flow {
                    emit(MockData.mockHouses)
                }

            // when
            everySuspend { mockQuidditchPlayersRepository.getAllHousesFlow() } returns mockGetAllHousesFlow

            // then
            setupQuidditchPlayersUseCase()
            quidditchPlayersUseCase.getHousesFromDB { houses ->

                // verify
                assertEquals(
                    MockData.mockHousesResponseWrapper,
                    houses,
                )
            }
        }

    @Test
    fun `getHousesFromDB throw exception`() =
        runTest {
            // given
            val mockGetAllHousesFlow: Flow<List<House>> =
                flow {
                    throw Exception("Some error")
                }

            // when
            everySuspend { mockQuidditchPlayersRepository.getAllHousesFlow() } returns mockGetAllHousesFlow

            // then
            setupQuidditchPlayersUseCase()
            quidditchPlayersUseCase.getHousesFromDB { houses ->

                // verify
                assertEquals(
                    MockData.mockHousesGenericErrorResponseWrapper,
                    houses,
                )
            }
        }

    @Test
    fun `fetchHousesApi happy success flow`() =
        runTest {
            // when
            everySuspend { mockQuidditchPlayersRepository.getAllHouses() } returns MockData.mockHousesResponseWrapper
            everySuspend { mockQuidditchPlayersRepository.createAllHousesToDB(MockData.mockHouses) } returns Unit

            // then
            setupQuidditchPlayersUseCase()
            val housesApiResponse = quidditchPlayersUseCase.fetchHousesApi()

            // verify
            verifySuspend { mockQuidditchPlayersRepository.createAllHousesToDB(MockData.mockHouses) }
            assertEquals(
                MockData.mockTrueResponseWrapper,
                housesApiResponse,
            )
        }

    @Test
    fun `fetchHousesApi then response error at getAllHouses error flow`() =
        runTest {
            // when
            everySuspend { mockQuidditchPlayersRepository.getAllHouses() } returns MockData.mockHousesGenericErrorResponseWrapper

            // then
            setupQuidditchPlayersUseCase()
            val housesApiResponse: ResponseWrapper<Boolean> =
                quidditchPlayersUseCase.fetchHousesApi()

            // verify
            assertEquals(
                MockData.mockBooleanResponseWrapperGenericError,
                housesApiResponse,
            )
        }

    @Test
    fun `fetchHousesApi throw exception at createAllHousesToDB error flow`() =
        runTest {
            // when
            everySuspend { mockQuidditchPlayersRepository.getAllHouses() } returns MockData.mockHousesResponseWrapper
            everySuspend { mockQuidditchPlayersRepository.createAllHousesToDB(MockData.mockHouses) } throws Exception(
                "Some error"
            )

            // then
            setupQuidditchPlayersUseCase()
            val housesApiResponse = quidditchPlayersUseCase.fetchHousesApi()

            // verify
            assertEquals(
                MockData.mockBooleanResponseWrapperGenericError,
                housesApiResponse,
            )
        }

    @Test
    fun `fetchPlayersAndPositionsApis happy success flow`() =
        runTest {
            // when
            everySuspend { mockQuidditchPlayersRepository.fetchPlayersAndPositions(HouseName.RAVENCLAW.name) } returns MockData.mockRavenclawPlayersAndPositionsResponseWrappers
            everySuspend {
                mockQuidditchPlayersRepository.createPlayersByHouseToDB(
                    MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions)
                )
            } returns Unit

            // then
            setupQuidditchPlayersUseCase()
            val playersAndPositionsApisResponse =
                quidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)

            // verify
            verifySuspend {
                mockQuidditchPlayersRepository.createPlayersByHouseToDB(
                    MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions)
                )
            }

            assertEquals(
                MockData.mockTrueResponseWrapper,
                playersAndPositionsApisResponse,
            )
        }

    @Test
    fun `fetchPlayersAndPositionsApis with players error response error flow`() =
        runTest {
            // when
            everySuspend { mockQuidditchPlayersRepository.fetchPlayersAndPositions(HouseName.RAVENCLAW.name) } returns MockData.mockRavenclawPlayersErrorAndPositionsResponseWrappers

            // then
            setupQuidditchPlayersUseCase()
            val playersAndPositionsApisResponse =
                quidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)

            // verify
            assertEquals(
                MockData.mockBooleanResponseWrapperGenericError,
                playersAndPositionsApisResponse,
            )
        }

    @Test
    fun `fetchPlayersAndPositionsApis with positions error response error flow`() =
        runTest {
            // when
            everySuspend { mockQuidditchPlayersRepository.fetchPlayersAndPositions(HouseName.RAVENCLAW.name) } returns MockData.mockRavenclawPlayersAndPositionsErrorResponseWrappers

            // then
            setupQuidditchPlayersUseCase()
            val playersAndPositionsApisResponse =
                quidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)

            // verify
            assertEquals(
                MockData.mockBooleanResponseWrapperGenericError,
                playersAndPositionsApisResponse,
            )
        }

    @Test
    fun `fetchPlayersAndPositionsApis throw exception at createAllHousesToDB error flow`() =
        runTest {
            // when
            everySuspend { mockQuidditchPlayersRepository.fetchPlayersAndPositions(HouseName.RAVENCLAW.name) } returns MockData.mockRavenclawPlayersAndPositionsResponseWrappers
            everySuspend {
                mockQuidditchPlayersRepository.createPlayersByHouseToDB(
                    MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions)
                )
            } throws Exception("Some error")

            // then
            setupQuidditchPlayersUseCase()
            val playersAndPositionsApisResponse =
                quidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)

            // verify
            verifySuspend(atMost(1)) {
                mockQuidditchPlayersRepository.createPlayersByHouseToDB(
                    MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions)
                )
            }
            assertEquals(
                MockData.mockBooleanResponseWrapperGenericError,
                playersAndPositionsApisResponse,
            )
        }

    @Test
    fun `getAllPlayersToDB happy success flow`() =
        runTest {
            // given
            val mockGetAllPlayersPlayerEntityFlow: Flow<List<PlayerEntity>> =
                flow {
                    emit(MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions))
                }

            // when
            everySuspend { mockQuidditchPlayersRepository.getAllPlayersFlow() } returns mockGetAllPlayersPlayerEntityFlow

            // then
            setupQuidditchPlayersUseCase()
            quidditchPlayersUseCase.getAllPlayersToDB { players ->

                // verify
                assertEquals(
                    MockData.mockRavenclawPlayersEntitiesResponseWrapper,
                    players,
                )
            }
        }

    @Test
    fun `getAllPlayersToDB throw getAllPlayersFlow error flow`() =
        runTest {
            // given
            val mockGetAllPlayersPlayerEntityFlow: Flow<List<PlayerEntity>> =
                flow {
                    throw Exception("Some error")
                }

            // when
            everySuspend { mockQuidditchPlayersRepository.getAllPlayersFlow() } returns mockGetAllPlayersPlayerEntityFlow

            // then
            setupQuidditchPlayersUseCase()
            quidditchPlayersUseCase.getAllPlayersToDB { players ->

                // verify
                assertEquals(
                    MockData.mockRavenclawPlayersEntitiesResponseWrapperError,
                    players,
                )
            }
        }

    @Test
    fun `fetchStatusByHouseName happy success flow`() =
        runTest {
            // when
            everySuspend { mockQuidditchPlayersRepository.fetchStatusByHouseName(HouseName.RAVENCLAW.name) } returns MockData.mockStatusResponseWrapper

            // then
            setupQuidditchPlayersUseCase()
            val status = quidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name)

            // verify
            assertEquals(
                MockData.mockStatusResponseWrapper,
                status,
            )
        }

    @Test
    fun `fetchStatusByPlayerId happy success flow`() =
        runTest {
            // given
            val playerId = MockData.ravenclawTeam().first().id

            // when
            everySuspend { mockQuidditchPlayersRepository.fetchStatusByPlayerId(playerId) } returns MockData.mockStatusResponseWrapper

            // then
            setupQuidditchPlayersUseCase()
            val status = quidditchPlayersUseCase.fetchStatusByPlayerId(playerId)

            // verify
            assertEquals(
                MockData.mockStatusResponseWrapper,
                status,
            )
        }

    @Test
    fun `set currentPlayer`() {
        val playerState =
            MockData
                .ravenclawTeam()
                .first()
                .toPlayerEntity(MockData.mockPositions)
                .toPlayerState()

        // then
        setupQuidditchPlayersUseCase()
        quidditchPlayersUseCase.setCurrentPlayer(playerState)

        // verify
        assertEquals(
            playerState,
            quidditchPlayersUseCase.getCurrentPlayer(),
        )
    }
}
