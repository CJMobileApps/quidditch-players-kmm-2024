package com.cjmobileapps.quidditch_players_kmm_2024.ui.houses.viewmodel

import com.cjmobileapps.quidditch_players_kmm_2024.data.MockData
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.HouseName
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrapper
import com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditch_players_kmm_2024.testutil.BaseTest
import com.cjmobileapps.quidditch_players_kmm_2024.util.TestCoroutineDispatchers
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.given
import kotlin.test.Test

class HouseViewModelImplTest : BaseTest() {
    private lateinit var housesViewModel: HousesViewModelImpl

    @Mock
    lateinit var mockQuidditchPlayersUseCase: QuidditchPlayersUseCase

    private val housesResponseWrapperArgumentCaptor =
        argumentCaptor<(ResponseWrapper<List<House>>) -> Unit>()

    private fun setupHouseViewModel() {
        housesViewModel =
            HousesViewModelImpl(
                quidditchPlayersUseCase = mockQuidditchPlayersUseCase,
                coroutineDispatchers = TestCoroutineDispatchers,
            )
    }

    @Test
    fun `house viewmodel apis never init`() {
        // then
        setupHouseViewModel()
        housesViewModel.goToPlayersListUi(HouseName.RAVENCLAW.name)
        var state = housesViewModel.getState()

        // verify
        assertTrue(state is HousesViewModelImpl.HousesState.LoadingState)

        // then
        var housesNavRouteUi = housesViewModel.getHousesNavRouteUiState()
        state = housesViewModel.getState()

        // verify
        assertTrue(state is HousesViewModelImpl.HousesState.LoadingState)
        assertTrue(housesNavRouteUi is HousesViewModelImpl.HousesNavRouteUi.Idle)

        // then
        housesViewModel.resetNavRouteUiToIdle()
        housesNavRouteUi = housesViewModel.getHousesNavRouteUiState()
        state = housesViewModel.getState()
        assertTrue(state is HousesViewModelImpl.HousesState.LoadingState)
        assertTrue(housesNavRouteUi is HousesViewModelImpl.HousesNavRouteUi.Idle)
    }

    @Test
    fun `fetch houses then go to player list happy flow`() =
        runTest {
            // then init setup
            setupHouseViewModel()
            var housesState = housesViewModel.getState()

            // verify
            assertTrue(housesState is HousesViewModelImpl.HousesState.LoadingState)

            // when
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchHousesApi())
                .thenReturn(MockData.mockTrueResponseWrapper)
            Mockito.`when`(
                mockQuidditchPlayersUseCase.getHousesFromDB(
                    housesResponseWrapperArgumentCaptor.capture()
                )
            ).thenReturn(Unit)

            // then
            setupHouseViewModel()
            housesResponseWrapperArgumentCaptor.firstValue.invoke(MockData.mockHousesResponseWrapper)
            housesState = housesViewModel.getState()

            // verify
            assertTrue((housesState is HousesViewModelImpl.HousesState.HousesLoadedState))
            if (housesState !is HousesViewModelImpl.HousesState.HousesLoadedState) return@runTest

            assertEquals(
                MockData.mockHouses,
                housesState.houses,
            )
            assertTrue(housesState.housesNavRouteUi.value is HousesViewModelImpl.HousesNavRouteUi.Idle)

            // then
            housesViewModel.goToPlayersListUi(HouseName.RAVENCLAW.name)

            // verify
            assertTrue(housesState.housesNavRouteUi.value is HousesViewModelImpl.HousesNavRouteUi.GoToPlayerListUi)
            val housesNavRouteUi =
                (housesState.housesNavRouteUi.value as HousesViewModelImpl.HousesNavRouteUi.GoToPlayerListUi)
            assertEquals(
                HouseName.RAVENCLAW.name,
                housesNavRouteUi.houseName,
            )
            assertEquals(
                "nav_players_list/RAVENCLAW",
                housesNavRouteUi.getNavRouteWithArguments(),
            )

            // then
            housesViewModel.resetNavRouteUiToIdle()

            // verify
            assertTrue(housesViewModel.getHousesNavRouteUiState() is HousesViewModelImpl.HousesNavRouteUi.Idle)
        }

    @Test
    fun `fetch houses then throw error at fetchHousesApi() error response flow`() =
        runTest {
            // then init setup
            setupHouseViewModel()
            var housesState = housesViewModel.getState()

            // verify
            assertTrue((housesState is HousesViewModelImpl.HousesState.LoadingState))

            // when
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchHousesApi())
                .thenReturn(MockData.mockBooleanResponseWrapperGenericError)
            Mockito.`when`(
                mockQuidditchPlayersUseCase.getHousesFromDB(
                    housesResponseWrapperArgumentCaptor.capture()
                )
            ).thenReturn(Unit)

            // then
            setupHouseViewModel()
            housesResponseWrapperArgumentCaptor.firstValue.invoke(MockData.mockHousesResponseWrapper)
            housesState = housesViewModel.getState()
            var snackbarState = housesViewModel.getSnackbarState()

            // verify
            assertTrue(housesState is HousesViewModelImpl.HousesState.HousesLoadedState)
            if ((housesState !is HousesViewModelImpl.HousesState.HousesLoadedState)) return@runTest
            assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.UnableToGetHousesListError)
            if (snackbarState !is HousesViewModelImpl.HousesSnackbarState.UnableToGetHousesListError) return@runTest
            assertEquals(
                MockData.mockHouses,
                housesState.houses,
            )
            assertTrue(housesState.housesNavRouteUi.value is HousesViewModelImpl.HousesNavRouteUi.Idle)
            assertNull(snackbarState.error)

            // then
            housesViewModel.resetSnackbarState()
            snackbarState = housesViewModel.getSnackbarState()

            // verify
            assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.Idle)
        }

    @Test
    fun `fetch houses then throw error at getHousesFromDB() error response flow`() =
        runTest {
            // then init setup
            setupHouseViewModel()
            var housesState = housesViewModel.getState()

            // verify
            assertTrue((housesState is HousesViewModelImpl.HousesState.LoadingState))

            // when
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchHousesApi())
                .thenReturn(MockData.mockTrueResponseWrapper)
            Mockito.`when`(
                mockQuidditchPlayersUseCase.getHousesFromDB(
                    housesResponseWrapperArgumentCaptor.capture()
                )
            ).thenReturn(Unit)

            // then
            setupHouseViewModel()
            housesResponseWrapperArgumentCaptor.firstValue.invoke(MockData.mockHousesGenericErrorResponseWrapper)
            housesState = housesViewModel.getState()
            var snackbarState = housesViewModel.getSnackbarState()

            // verify
            assertTrue(housesState is HousesViewModelImpl.HousesState.HousesLoadedState)
            if ((housesState !is HousesViewModelImpl.HousesState.HousesLoadedState)) return@runTest
            assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.UnableToGetHousesListError)
            if ((snackbarState !is HousesViewModelImpl.HousesSnackbarState.UnableToGetHousesListError)) return@runTest
            assertEquals(
                emptyList<List<House>>(),
                housesState.houses,
            )
            assertTrue(housesState.housesNavRouteUi.value is HousesViewModelImpl.HousesNavRouteUi.Idle)
            assertNull(snackbarState.error)

            // then
            housesViewModel.resetSnackbarState()
            snackbarState = housesViewModel.getSnackbarState()

            // verify
            assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.Idle)
        }

    @Test
    fun `fetch houses then throw exception at fetchHousesApi() error response flow`() =
        runTest {
            // then init setup
            setupHouseViewModel()
            var housesState = housesViewModel.getState()

            // verify
            assertTrue((housesState is HousesViewModelImpl.HousesState.LoadingState))

            // when
//        Mockito.`when`(mockQuidditchPlayersUseCase.fetchHousesApi()).thenThrow(Exception("Some error"))
            given(mockQuidditchPlayersUseCase.fetchHousesApi()).willAnswer {
                Exception("Some error")
            }
            Mockito.`when`(
                mockQuidditchPlayersUseCase.getHousesFromDB(
                    housesResponseWrapperArgumentCaptor.capture()
                )
            ).thenReturn(Unit)

            // then
            setupHouseViewModel()
            housesResponseWrapperArgumentCaptor.firstValue.invoke(MockData.mockHousesResponseWrapper)
            housesState = housesViewModel.getState()
            var snackbarState = housesViewModel.getSnackbarState()

            // verify
            assertTrue(housesState is HousesViewModelImpl.HousesState.HousesLoadedState)
            if ((housesState !is HousesViewModelImpl.HousesState.HousesLoadedState)) return@runTest
            assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.ShowGenericError)
            if ((snackbarState !is HousesViewModelImpl.HousesSnackbarState.ShowGenericError)) return@runTest
            assertEquals(
                MockData.mockHouses,
                housesState.houses,
            )
            assertTrue(housesState.housesNavRouteUi.value is HousesViewModelImpl.HousesNavRouteUi.Idle)
            assertNull(snackbarState.error)

            // then
            housesViewModel.resetSnackbarState()
            snackbarState = housesViewModel.getSnackbarState()

            // verify
            assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.Idle)
        }
}
