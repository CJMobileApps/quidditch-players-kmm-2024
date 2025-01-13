package com.cjmobileapps.quidditch_players_kmm_2024.ui.playerdetail

import androidx.core.bundle.Bundle
import com.cjmobileapps.quidditch_players_kmm_2024.data.MockData
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.toPlayersState
import com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditch_players_kmm_2024.testutil.BaseTest
import com.cjmobileapps.quidditch_players_kmm_2024.ui.playerdetail.viewmodel.PlayerDetailViewModel
import com.cjmobileapps.quidditch_players_kmm_2024.ui.playerdetail.viewmodel.PlayerDetailViewModelImpl
import com.cjmobileapps.quidditch_players_kmm_2024.util.TestCoroutineDispatchers
import com.cjmobileapps.quidditch_players_kmm_2024.util.TestTimeUtil
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.given
import kotlin.test.Test

class PlayerDetailViewModelImplTest : BaseTest() {
    private lateinit var playerDetailViewModel: PlayerDetailViewModel

    @Mock
    private lateinit var mockBundle: Bundle

    @Mock
    private lateinit var mockQuidditchPlayersUseCase: QuidditchPlayersUseCase

    private val testTimeUtil = TestTimeUtil

    private fun setupPlayerDetailViewModel() {
        testTimeUtil.resetTestTimeUtil()
        playerDetailViewModel =
            PlayerDetailViewModelImpl(
                quidditchPlayersUseCase = mockQuidditchPlayersUseCase,
                bundle = mockBundle,
                timeUtil = testTimeUtil,
                coroutineDispatchers = TestCoroutineDispatchers,
            )
    }

    @Test
    fun `fetch player happy flow`() =
        runTest {
            // given
            val mockRavenPlayer = MockData.mockRavenclawPlayersEntities.toPlayersState().first()
            val mockRavenPlayerId = mockRavenPlayer.id

            // when
            Mockito.`when`(mockBundle.getString("playerId")).thenReturn(mockRavenPlayerId)
            Mockito.`when`(mockQuidditchPlayersUseCase.getCurrentPlayer()).thenReturn(mockRavenPlayer)
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByPlayerId(mockRavenPlayerId)).thenReturn(MockData.mockStatusResponseWrapper)

            // then init setup
            setupPlayerDetailViewModel()
            val playerDetailState = playerDetailViewModel.getState()
            val snackbarState = playerDetailViewModel.getSnackbarState()

            // verify
            assertTrue(snackbarState is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.Idle)
            assertTrue(playerDetailState is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState)
            if (playerDetailState !is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState) return@runTest
            val player = playerDetailState.player
            val topBarTitle = playerDetailViewModel.getTopBarTitle()

            assertEquals(
                mockRavenPlayer.id,
                player?.id,
            )
            assertEquals(
                mockRavenPlayer.favoriteSubject,
                player?.favoriteSubject,
            )
            assertEquals(
                mockRavenPlayer.firstName,
                player?.firstName,
            )
            assertEquals(
                mockRavenPlayer.lastName,
                player?.lastName,
            )
            assertEquals(
                mockRavenPlayer.yearsPlayed,
                player?.yearsPlayed,
            )
            assertEquals(
                mockRavenPlayer.status.value,
                player?.status?.value,
            )
            assertEquals(
                mockRavenPlayer.getFullName(),
                topBarTitle,
            )
        }

    @Test
    fun `fetch player then return status error flow`() =
        runTest {
            // given
            val mockRavenPlayer = MockData.mockRavenclawPlayersEntities.toPlayersState().first()
            val mockRavenPlayerId = mockRavenPlayer.id.toString()

            // when
            Mockito.`when`(mockBundle.getString("playerId")).thenReturn(mockRavenPlayerId)
            Mockito.`when`(mockQuidditchPlayersUseCase.getCurrentPlayer()).thenReturn(mockRavenPlayer)
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByPlayerId(mockRavenPlayerId)).thenReturn(MockData.mockStatusResponseWrapperGenericError)

            // then init setup
            setupPlayerDetailViewModel()
            val playerDetailState = playerDetailViewModel.getState()
            val snackbarState = playerDetailViewModel.getSnackbarState()

            // verify
            assertTrue(snackbarState is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.Idle)
            assertTrue(playerDetailState is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState)
            if (playerDetailState !is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState) return@runTest
            val player = playerDetailState.player
            val topBarTitle = playerDetailViewModel.getTopBarTitle()

            assertEquals(
                mockRavenPlayer.id,
                player?.id,
            )
            assertEquals(
                mockRavenPlayer.favoriteSubject,
                player?.favoriteSubject,
            )
            assertEquals(
                mockRavenPlayer.firstName,
                player?.firstName,
            )
            assertEquals(
                mockRavenPlayer.lastName,
                player?.lastName,
            )
            assertEquals(
                mockRavenPlayer.yearsPlayed,
                player?.yearsPlayed,
            )
            assertEquals(
                mockRavenPlayer.status.value,
                player?.status?.value,
            )
            assertEquals(
                mockRavenPlayer.getFullName(),
                topBarTitle,
            )
        }

    @Test
    fun `fetch player then throw status error flow`() =
        runTest {
            // given
            val mockRavenPlayer = MockData.mockRavenclawPlayersEntities.toPlayersState().first()
            val mockRavenPlayerId = mockRavenPlayer.id

            // when
            Mockito.`when`(mockBundle.getString("playerId")).thenReturn(mockRavenPlayerId)
            Mockito.`when`(mockQuidditchPlayersUseCase.getCurrentPlayer()).thenReturn(mockRavenPlayer)
            given(mockQuidditchPlayersUseCase.fetchStatusByPlayerId(mockRavenPlayerId)).willAnswer {
                throw Exception("Some error")
            }

            // then init setup
            setupPlayerDetailViewModel()
            val playerDetailState = playerDetailViewModel.getState()
            val snackbarState = playerDetailViewModel.getSnackbarState()

            // verify
            // this assertion doesn't work not sure why
            assertTrue(snackbarState is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.ShowGenericError)
            assertTrue(playerDetailState is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState)
            if (playerDetailState !is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState) return@runTest
            val player = playerDetailState.player
            val topBarTitle = playerDetailViewModel.getTopBarTitle()

            assertEquals(
                mockRavenPlayer.id,
                player?.id,
            )
            assertEquals(
                mockRavenPlayer.favoriteSubject,
                player?.favoriteSubject,
            )
            assertEquals(
                mockRavenPlayer.firstName,
                player?.firstName,
            )
            assertEquals(
                mockRavenPlayer.lastName,
                player?.lastName,
            )
            assertEquals(
                mockRavenPlayer.yearsPlayed,
                player?.yearsPlayed,
            )
            assertEquals(
                mockRavenPlayer.status.value,
                player?.status?.value,
            )
            assertEquals(
                mockRavenPlayer.getFullName(),
                topBarTitle,
            )
        }

    @Test
    fun `throw fetching player is null flow`() =
        runTest {
            // given
            val mockRavenPlayer = MockData.mockRavenclawPlayersEntities.toPlayersState().first()
            val mockRavenPlayerId = mockRavenPlayer.id.toString()

            // when
            Mockito.`when`(mockBundle.getString("playerId")).thenReturn(mockRavenPlayerId)
            Mockito.`when`(mockQuidditchPlayersUseCase.getCurrentPlayer()).thenReturn(null)
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByPlayerId(mockRavenPlayerId)).thenReturn(MockData.mockStatusResponseWrapper)

            // then init setup
            setupPlayerDetailViewModel()
            val playerDetailState = playerDetailViewModel.getState()
            var snackbarState = playerDetailViewModel.getSnackbarState()

            // verify
            assertTrue(playerDetailState is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState)
            if (playerDetailState !is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState) return@runTest
            assertTrue(snackbarState is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.UnableToGetPlayerError)

            // then
            playerDetailViewModel.resetSnackbarState()
            snackbarState = playerDetailViewModel.getSnackbarState()
            assertTrue(snackbarState is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.Idle)
        }
}
