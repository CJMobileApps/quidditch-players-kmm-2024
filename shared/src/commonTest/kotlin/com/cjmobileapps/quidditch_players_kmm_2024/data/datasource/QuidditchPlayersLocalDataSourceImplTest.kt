package com.cjmobileapps.quidditch_players_kmm_2024.data.datasource

import com.cjmobileapps.quidditch_players_kmm_2024.data.MockData
import com.cjmobileapps.quidditch_players_kmm_2024.datasource.QuidditchPlayersLocalDataSource
import com.cjmobileapps.quidditch_players_kmm_2024.datasource.QuidditchPlayersLocalDataSourceImpl
import com.cjmobileapps.quidditch_players_kmm_2024.room.QuidditchPlayersDao
import com.cjmobileapps.quidditch_players_kmm_2024.util.TestCoroutineDispatchers
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class QuidditchPlayersLocalDataSourceImplTest {
    private lateinit var quidditchPlayersLocalDataSource: QuidditchPlayersLocalDataSource

    private val mockQuidditchPlayersDao: QuidditchPlayersDao = mock()

    private fun setupQuidditchPlayersLocalDataSource() {
        quidditchPlayersLocalDataSource =
            QuidditchPlayersLocalDataSourceImpl(
                quidditchPlayersDao = mockQuidditchPlayersDao,
                coroutineDispatchers = TestCoroutineDispatchers,
            )
    }

    @Test
    fun `getAllHousesFlow happy success flow`() =
        runTest {
            // given
            everySuspend { mockQuidditchPlayersDao.getAllHouses() } returns MockData.mockHouses

            // then
            setupQuidditchPlayersLocalDataSource()
            val houses = quidditchPlayersLocalDataSource.getAllHousesFlow().first()

            // verify
            assertEquals(
                MockData.mockHouses,
                houses,
            )
        }

    @Test
    fun `createAllHouses happy success flow`() =
        runTest {
            // when
            everySuspend { mockQuidditchPlayersDao.deleteAllHouses() } returns Unit
            everySuspend { mockQuidditchPlayersDao.insertAllHouses(MockData.mockHouses) } returns Unit

            // then
            setupQuidditchPlayersLocalDataSource()
            quidditchPlayersLocalDataSource.createAllHouses(MockData.mockHouses)

            verifySuspend { mockQuidditchPlayersDao.deleteAllHouses() }
            verifySuspend { mockQuidditchPlayersDao.insertAllHouses(MockData.mockHouses) }
        }

    @Test
    fun `getAllPlayersFlow happy success flow`() =
        runTest {
            // given
            everySuspend { mockQuidditchPlayersDao.getAllPlayers() } returns MockData.mockPlayersEntities

            // then
            setupQuidditchPlayersLocalDataSource()
            val players = quidditchPlayersLocalDataSource.getAllPlayersFlow().first()

            // verify
            assertEquals(
                MockData.mockPlayersEntities,
                players,
            )
        }

    @Test
    fun `createPlayersByHouseToDB happy success flow`() =
        runTest {
            // when
            everySuspend { mockQuidditchPlayersDao.deleteAllPlayers() } returns Unit
            everySuspend { mockQuidditchPlayersDao.insertAllPlayers(MockData.mockPlayersEntities) } returns Unit

            // then
            setupQuidditchPlayersLocalDataSource()
            quidditchPlayersLocalDataSource.createPlayersByHouseToDB(MockData.mockPlayersEntities)

            // verify
            verifySuspend { mockQuidditchPlayersDao.deleteAllPlayers() }
            verifySuspend { mockQuidditchPlayersDao.insertAllPlayers(MockData.mockPlayersEntities) }
        }
}
