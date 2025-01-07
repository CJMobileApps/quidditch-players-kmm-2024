package com.cjmobileapps.quidditch_players_kmm_2024.ui.houses.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.cjmobileapps.quidditch_players_kmm_2024.NavItem
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditch_players_kmm_2024.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.quidditch_players_kmm_2024.util.onError
import com.cjmobileapps.quidditch_players_kmm_2024.util.onSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class HousesViewModelImpl(
        private val quidditchPlayersUseCase: QuidditchPlayersUseCase,
        coroutineDispatchers: CoroutineDispatchers,
    ) : ViewModel(), HousesViewModel {
        private val compositeJob = Job()

        private val exceptionHandler =
            CoroutineExceptionHandler { _, throwable ->
                Logger.e(tag) { "coroutineExceptionHandler() error occurred: $throwable \n ${throwable.message}"}
                snackbarState.value = HousesSnackbarState.ShowGenericError()
            }

        private val coroutineContext =
            compositeJob + coroutineDispatchers.main + exceptionHandler + SupervisorJob()

        private val coroutineContextHousesFlow =
            compositeJob + coroutineDispatchers.main + exceptionHandler + SupervisorJob()

        private val housesState = mutableStateOf<HousesState>(HousesState.LoadingState)

        private val snackbarState = mutableStateOf<HousesSnackbarState>(HousesSnackbarState.Idle)

        private val tag = HousesViewModelImpl::class.simpleName ?: ""

        override fun getState() = housesState.value

        override fun getSnackbarState() = snackbarState.value

        init {
            viewModelScope.launch(coroutineContext) {
                quidditchPlayersUseCase
                    .fetchHousesApi()
                    .onError { _, _ ->
                        housesState.value = HousesState.HousesLoadedState()
                        snackbarState.value = HousesSnackbarState.UnableToGetHousesListError()
                    }
            }

            viewModelScope.launch(coroutineContextHousesFlow) {
                quidditchPlayersUseCase.getHousesFromDB { housesResponse ->
                    housesResponse
                        .onSuccess { houses ->
                            housesState.value = HousesState.HousesLoadedState(houses = houses)
                        }
                        .onError { _, _ ->
                            housesState.value = HousesState.HousesLoadedState()
                            snackbarState.value = HousesSnackbarState.UnableToGetHousesListError()
                        }
                }
            }
        }

        override fun resetSnackbarState() {
            snackbarState.value = HousesSnackbarState.Idle
        }

        override fun goToPlayersListUi(houseName: String) {
            val state = getState()
            if (state !is HousesState.HousesLoadedState) return
            state.housesNavRouteUi.value = HousesNavRouteUi.GoToPlayerListUi(houseName)
        }

        override fun getHousesNavRouteUiState(): HousesNavRouteUi {
            val state = getState()
            if (state !is HousesState.HousesLoadedState) return HousesNavRouteUi.Idle
            return state.housesNavRouteUi.value
        }

        override fun resetNavRouteUiToIdle() {
            val state = getState()
            if (state !is HousesState.HousesLoadedState) return
            state.housesNavRouteUi.value = HousesNavRouteUi.Idle
        }

        sealed class HousesState {
            data object LoadingState : HousesState()

            data class HousesLoadedState(
                val houses: List<House> = emptyList(),
                val housesNavRouteUi: MutableState<HousesNavRouteUi> = mutableStateOf(
                    HousesNavRouteUi.Idle
                ),
            ) : HousesState()
        }

        sealed class HousesSnackbarState {
            data object Idle : HousesSnackbarState()

            data class ShowGenericError(
                val error: String? = null,
            ) : HousesSnackbarState()

            data class UnableToGetHousesListError(
                val error: String? = null,
            ) : HousesSnackbarState()
        }

        sealed class HousesNavRouteUi {
            data object Idle : HousesNavRouteUi()

            data class GoToPlayerListUi(val houseName: String) : HousesNavRouteUi() {
                fun getNavRouteWithArguments(): String = NavItem.PlayersList.getNavRouteWithArguments(houseName)
            }
        }
    }
