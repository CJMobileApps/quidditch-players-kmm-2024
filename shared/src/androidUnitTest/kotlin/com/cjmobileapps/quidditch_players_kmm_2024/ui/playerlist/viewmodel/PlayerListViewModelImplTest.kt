package com.cjmobileapps.quidditch_players_kmm_2024.ui.playerlist.viewmodel

import android.os.Bundle
import com.cjmobileapps.quidditch_players_kmm_2024.data.MockData
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.HouseName
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrapper
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Status
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.toPlayersState
import com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditch_players_kmm_2024.testutil.BaseTest
import com.cjmobileapps.quidditch_players_kmm_2024.ui.playerslist.viewmodel.PlayersListViewModel
import com.cjmobileapps.quidditch_players_kmm_2024.ui.playerslist.viewmodel.PlayersListViewModelImpl
import com.cjmobileapps.quidditch_players_kmm_2024.util.TestCoroutineDispatchers
import com.cjmobileapps.quidditch_players_kmm_2024.util.TestTimeUtil
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import java.net.HttpURLConnection
import java.util.UUID
import kotlin.test.Test

class PlayerListViewModelImplTest : BaseTest() {
    private lateinit var playerLiveViewModel: PlayersListViewModel

    @Mock
    private lateinit var mockBundle: Bundle

    @Mock
    private lateinit var mockQuidditchPlayersUseCase: QuidditchPlayersUseCase

    private val playerEntityResponseWrapperArgumentCaptor =
        argumentCaptor<(ResponseWrapper<List<PlayerEntity>>) -> Unit>()

    private val testTimeUtil = TestTimeUtil

    private fun setupPlayerListViewModel() {
        // todo go through unit test and reset testTimeUtil
        playerLiveViewModel =
            PlayersListViewModelImpl(
                bundle = mockBundle,
                quidditchPlayersUseCase = mockQuidditchPlayersUseCase,
                timeUtil = testTimeUtil,
                coroutineDispatchers = TestCoroutineDispatchers,
            )
    }

    @Test
    fun `fetch players happy then goToPlayerDetailUi flow`() =
        runTest {
            // given
            val mockRavenPlayers = MockData.mockRavenclawPlayersEntities.toPlayersState()

            // when
            Mockito.`when`(mockBundle.getString("houseName")).thenReturn(HouseName.RAVENCLAW.name)

            // then init setup
            setupPlayerListViewModel()
            var playerListState = playerLiveViewModel.getState()

            // verify
            assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.LoadingState)

            // when
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockTrueResponseWrapper)
            Mockito.`when`(mockQuidditchPlayersUseCase.getAllPlayersToDB(playerEntityResponseWrapperArgumentCaptor.capture())).thenReturn(Unit)
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockStatusResponseWrapper)

            // then
            setupPlayerListViewModel()
            playerEntityResponseWrapperArgumentCaptor.firstValue.invoke(MockData.mockRavenclawPlayersEntitiesResponseWrapper)
            playerListState = playerLiveViewModel.getState()
            val snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.Idle)
            assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState)
            if (playerListState !is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState) return@runTest
            assertTrue(playerListState.playersNavRouteUi.value is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)

            playerListState.players.forEachIndexed { index, playerState ->
                assertEquals(
                    mockRavenPlayers[index].id,
                    playerState.id,
                )
                assertEquals(
                    mockRavenPlayers[index].favoriteSubject,
                    playerState.favoriteSubject,
                )
                assertEquals(
                    mockRavenPlayers[index].firstName,
                    playerState.firstName,
                )
                assertEquals(
                    mockRavenPlayers[index].lastName,
                    playerState.lastName,
                )
                assertEquals(
                    mockRavenPlayers[index].yearsPlayed,
                    playerState.yearsPlayed,
                )
                // todo fix unit test when adding reset time feature
//                if (index != 0) {
//                    Assertions.assertEquals(
//                        mockRavenPlayers[index].status.value,
//                        playerState.status.value,
//                    )
//                } else {
//                    Assertions.assertEquals(
//                        MockData.mockStatus().status,
//                        playerState.status.value,
//                    )
//                }
            }

            // then
            playerLiveViewModel.goToPlayerDetailUi(playerListState.players.first())

            // verify
            assertTrue(playerListState.playersNavRouteUi.value is PlayersListViewModelImpl.PlayersListNavRouteUi.GoToPlayerDetailUi)
            val playerListNavRouteUi = (playerListState.playersNavRouteUi.value as PlayersListViewModelImpl.PlayersListNavRouteUi.GoToPlayerDetailUi)
            assertEquals(
                mockRavenPlayers.first().id.toString(),
                playerListNavRouteUi.playerId,
            )
            assertEquals(
                "nav_player_detail/${mockRavenPlayers.first().id}",
                playerListNavRouteUi.getNavRouteWithArguments(),
            )

            // then
            playerLiveViewModel.resetNavRouteUiToIdle()

            // verify
            assertTrue(playerLiveViewModel.getPlayersListNavRouteUiState() is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)
        }

    @Test
    fun `fetch players happy then throw error at fetchPlayersAndPositionsApis error response flow`() =
        runTest {
            // given
            val mockRavenPlayers = MockData.mockRavenclawPlayersEntities.toPlayersState()

            // when
            Mockito.`when`(mockBundle.getString("houseName")).thenReturn(HouseName.RAVENCLAW.name)

            // then init setup
            setupPlayerListViewModel()
            var playerListState = playerLiveViewModel.getState()

            // verify
            assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.LoadingState)

            // when
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockBooleanResponseWrapperGenericError)
            Mockito.`when`(mockQuidditchPlayersUseCase.getAllPlayersToDB(playerEntityResponseWrapperArgumentCaptor.capture())).thenReturn(Unit)
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockStatusResponseWrapper)

            // then
            setupPlayerListViewModel()
            playerEntityResponseWrapperArgumentCaptor.firstValue.invoke(MockData.mockRavenclawPlayersEntitiesResponseWrapper)
            playerListState = playerLiveViewModel.getState()
            var snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState)
            if (playerListState !is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState) return@runTest
            assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.UnableToGetPlayersListError)
            if (snackbarState !is PlayersListViewModelImpl.PlayersListSnackbarState.UnableToGetPlayersListError) return@runTest
            assertTrue(playerListState.playersNavRouteUi.value is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)
            assertNull(snackbarState.error)

            playerListState.players.forEachIndexed { index, playerState ->
                assertEquals(
                    mockRavenPlayers[index].id,
                    playerState.id,
                )
                assertEquals(
                    mockRavenPlayers[index].favoriteSubject,
                    playerState.favoriteSubject,
                )
                assertEquals(
                    mockRavenPlayers[index].firstName,
                    playerState.firstName,
                )
                assertEquals(
                    mockRavenPlayers[index].lastName,
                    playerState.lastName,
                )
                assertEquals(
                    mockRavenPlayers[index].yearsPlayed,
                    playerState.yearsPlayed,
                )
                assertEquals(
                    mockRavenPlayers[index].status.value,
                    playerState.status.value,
                )
            }

            // then
            playerLiveViewModel.resetNavRouteUiToIdle()
            playerLiveViewModel.resetSnackbarState()
            snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.Idle)
            assertTrue(playerLiveViewModel.getPlayersListNavRouteUiState() is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)
        }

    @Test
    fun `fetch players happy then throw error at getHousesFromDB() error response flow`() =
        runTest {
            // when
            Mockito.`when`(mockBundle.getString("houseName")).thenReturn(HouseName.RAVENCLAW.name)

            // then init setup
            setupPlayerListViewModel()
            var playerListState = playerLiveViewModel.getState()

            // verify
            assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.LoadingState)

            // when
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockTrueResponseWrapper)
            Mockito.`when`(mockQuidditchPlayersUseCase.getAllPlayersToDB(playerEntityResponseWrapperArgumentCaptor.capture())).thenReturn(Unit)
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockStatusResponseWrapper)

            // then
            setupPlayerListViewModel()
            playerEntityResponseWrapperArgumentCaptor.firstValue.invoke(MockData.mockRavenclawPlayersEntitiesResponseWrapperError)
            playerListState = playerLiveViewModel.getState()
            var snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState)
            if (playerListState !is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState) return@runTest
            assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.UnableToGetPlayersListError)
            if (snackbarState !is PlayersListViewModelImpl.PlayersListSnackbarState.UnableToGetPlayersListError) return@runTest
            assertTrue(playerListState.playersNavRouteUi.value is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)
            assertNull(snackbarState.error)
            assertTrue(playerListState.players.isEmpty())

            // then
            playerLiveViewModel.resetNavRouteUiToIdle()
            playerLiveViewModel.resetSnackbarState()
            snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.Idle)
            assertTrue(playerLiveViewModel.getPlayersListNavRouteUiState() is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)
        }

    @Test
    fun `fetchStatusByHouseName() happy flow`() =
        runTest {
            // given
            val mockRavenPlayers = MockData.mockRavenclawPlayersEntities.toPlayersState()

            // when
            Mockito.`when`(mockBundle.getString("houseName")).thenReturn(HouseName.RAVENCLAW.name)
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockStatusResponseWrapper)

            // then
            setupPlayerListViewModel()
            playerLiveViewModel.fetchStatusByHouseName(mockRavenPlayers)

            // verify
            assertEquals(
                MockData.mockStatus().status,
                mockRavenPlayers.first().status.value,
            )
        }

    @Test
    fun `fetchStatusByHouseName() player not found flow`() =
        runTest {
            // given
            val mockRavenPlayers = MockData.mockRavenclawPlayersEntities.toPlayersState()

            val name = "Harry Potters"
            val mockStatus =
                Status(
                    playerId = UUID.randomUUID().toString(),
                    status = MockData.getStatus(name),
                )

            val mockStatusResponseWrapper =
                ResponseWrapper(
                    data = mockStatus,
                    statusCode = HttpURLConnection.HTTP_OK,
                )

            // when
            Mockito.`when`(mockBundle.getString("houseName")).thenReturn(HouseName.RAVENCLAW.name)
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name)).thenReturn(mockStatusResponseWrapper)

            // then
            setupPlayerListViewModel()
            playerLiveViewModel.fetchStatusByHouseName(mockRavenPlayers)

            // verify
            assertEquals(
                "",
                mockRavenPlayers.first().status.value,
            )
        }

    @Test
    fun `fetchStatusByHouseName() returns error flow`() =
        runTest {
            // given
            val mockRavenPlayers = MockData.mockRavenclawPlayersEntities.toPlayersState()

            // when
            Mockito.`when`(mockBundle.getString("houseName")).thenReturn(HouseName.RAVENCLAW.name)
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockStatusResponseWrapperGenericError)

            // then
            setupPlayerListViewModel()
            playerLiveViewModel.fetchStatusByHouseName(mockRavenPlayers)

            // verify
            assertEquals(
                "",
                mockRavenPlayers.first().status.value,
            )
        }

    @Test
    fun `getTopBarTitle() test`() =
        runTest {
            // when
            Mockito.`when`(mockBundle.getString("houseName")).thenReturn(HouseName.RAVENCLAW.name)

            // then init setup
            setupPlayerListViewModel()
            val name = playerLiveViewModel.getTopBarTitle()

            // verify
            assertEquals(
                HouseName.RAVENCLAW.name,
                name,
            )
        }

    @Test
    fun `house viewmodel apis never init`() =
        runTest {
            // given
            val mockRavenPlayers = MockData.mockRavenclawPlayersEntities.toPlayersState()

            // when
            Mockito.`when`(mockBundle.getString("houseName")).thenReturn("")

            // then init setup
            setupPlayerListViewModel()
            playerLiveViewModel.goToPlayerDetailUi(mockRavenPlayers.first())
            playerLiveViewModel.getPlayersListNavRouteUiState()
            playerLiveViewModel.resetNavRouteUiToIdle()
            val playerListState = playerLiveViewModel.getState()
            playerLiveViewModel.resetSnackbarState()
            val snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.LoadingState)
            assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.Idle)
        }
}
