package com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers

import com.cjmobileapps.quidditch_players_kmm_2024.data.MockData
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.HouseName
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity
import com.cjmobileapps.quidditch_players_kmm_2024.datasource.QuidditchPlayersApiDataSource
import com.cjmobileapps.quidditch_players_kmm_2024.datasource.QuidditchPlayersLocalDataSource
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class QuidditchPlayersRepositoryImplTest {
    private lateinit var quidditchPlayersRepositoryImpl: QuidditchPlayersRepositoryImpl

    private val mockQuidditchPlayersApiDataSource = mock<QuidditchPlayersApiDataSource>()

    private val mockQuidditchPlayersLocalDataSource = mock<QuidditchPlayersLocalDataSource>()

    private fun setupQuidditchPlayersRepositoryImpl() {
        quidditchPlayersRepositoryImpl =
            QuidditchPlayersRepositoryImpl(
                quidditchPlayersApiDataSource = mockQuidditchPlayersApiDataSource,
                quidditchPlayersLocalDataSource = mockQuidditchPlayersLocalDataSource,
            )
    }

    @Test
    fun `getAllHouses happy success flow`() =
        runTest {
            // Given
            everySuspend { mockQuidditchPlayersApiDataSource.getAllHouses() } returns MockData.mockHousesResponseWrapper

            // then
            setupQuidditchPlayersRepositoryImpl()
            val houses = quidditchPlayersRepositoryImpl.getAllHouses()

            // verify
            assertEquals(
                MockData.mockHousesResponseWrapper,
                houses,
            )
        }

    @Test
    fun `getAllHousesFlow happy success flow`() =
        runTest {
            // given
            val mockGetAllHousesFlow: Flow<List<House>> =
                flow {
                    emit(MockData.mockHouses)
                }
            everySuspend { mockQuidditchPlayersLocalDataSource.getAllHousesFlow() } returns mockGetAllHousesFlow

            // then
            setupQuidditchPlayersRepositoryImpl()
            val houses = quidditchPlayersRepositoryImpl.getAllHousesFlow().first()

            // verify
            assertEquals(
                MockData.mockHouses,
                houses,
            )
        }

    @Test
    fun `createPlayersByHouseToDB happy success flow`() =
        runTest {
            // then
            setupQuidditchPlayersRepositoryImpl()
            everySuspend { mockQuidditchPlayersLocalDataSource.createPlayersByHouseToDB(MockData.mockPlayersEntities) } returns Unit
            quidditchPlayersRepositoryImpl.createPlayersByHouseToDB(MockData.mockPlayersEntities)

            // verify
            verifySuspend { mockQuidditchPlayersLocalDataSource.createPlayersByHouseToDB(MockData.mockPlayersEntities) }
        }

    @Test
    fun `getPlayersByHouse happy success flow`() =
        runTest {
            // given
            everySuspend { mockQuidditchPlayersApiDataSource.getPlayersByHouse(HouseName.RAVENCLAW.name) } returns MockData.mockRavenclawPlayersResponseWrapper

            // then
            setupQuidditchPlayersRepositoryImpl()
            val players = quidditchPlayersRepositoryImpl.getPlayersByHouse(HouseName.RAVENCLAW.name)

            // verify
            assertEquals(
                MockData.mockRavenclawPlayersResponseWrapper,
                players,
            )
        }

    @Test
    fun `createAllHousesToDB happy success flow`() =
        runTest {
            // then
            setupQuidditchPlayersRepositoryImpl()
            everySuspend { mockQuidditchPlayersLocalDataSource.createAllHouses(MockData.mockHouses) } returns Unit
            quidditchPlayersRepositoryImpl.createAllHousesToDB(MockData.mockHouses)

            // verify
            verifySuspend { mockQuidditchPlayersLocalDataSource.createAllHouses(MockData.mockHouses) }
        }

    @Test
    fun `getAllPlayersFlow happy success flow`() =
        runTest {
            val mockGetAllPlayersEntitiesFlow: Flow<List<PlayerEntity>> =
                flow {
                    emit(MockData.mockPlayersEntities)
                }
            everySuspend { mockQuidditchPlayersLocalDataSource.getAllPlayersFlow() } returns mockGetAllPlayersEntitiesFlow

            // then
            setupQuidditchPlayersRepositoryImpl()
            val players = quidditchPlayersRepositoryImpl.getAllPlayersFlow().first()

            // verify
            assertEquals(
                MockData.mockPlayersEntities,
                players,
            )
        }

    @Test
    fun `fetchPlayersAndPositions happy success flow`() =
        runTest {
            // given
            everySuspend { mockQuidditchPlayersApiDataSource.fetchPlayersAndPositions(HouseName.RAVENCLAW.name) } returns MockData.mockRavenclawPlayersAndPositionsResponseWrappers

            // then
            setupQuidditchPlayersRepositoryImpl()
            val playersAndPositions =
                quidditchPlayersRepositoryImpl.fetchPlayersAndPositions(HouseName.RAVENCLAW.name)

            // verify
            assertEquals(
                MockData.mockRavenclawPlayersAndPositionsResponseWrappers,
                playersAndPositions,
            )
        }

    @Test
    fun `fetchStatusByHouseName happy success flow`() =
        runTest {
            // when
            everySuspend { mockQuidditchPlayersApiDataSource.getStatusByHouseName(HouseName.RAVENCLAW.name) } returns MockData.mockStatusResponseWrapper

            // then
            setupQuidditchPlayersRepositoryImpl()
            val status =
                quidditchPlayersRepositoryImpl.fetchStatusByHouseName(HouseName.RAVENCLAW.name)

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
            val playerId = MockData.ravenclawTeam().first().id.toString()

            // when
            everySuspend { mockQuidditchPlayersApiDataSource.fetchStatusByPlayerId(playerId) } returns MockData.mockStatusResponseWrapper

            // then
            setupQuidditchPlayersRepositoryImpl()
            val status = quidditchPlayersRepositoryImpl.fetchStatusByPlayerId(playerId = playerId)

            // verify
            assertEquals(
                MockData.mockStatusResponseWrapper,
                status,
            )
        }
}
