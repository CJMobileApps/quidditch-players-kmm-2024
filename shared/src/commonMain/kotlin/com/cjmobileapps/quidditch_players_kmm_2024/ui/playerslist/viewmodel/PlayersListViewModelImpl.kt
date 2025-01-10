package com.cjmobileapps.quidditch_players_kmm_2024.ui.playerslist.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.bundle.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerState
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.toPlayersState
import com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditch_players_kmm_2024.ui.NavItem
import com.cjmobileapps.quidditch_players_kmm_2024.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.quidditch_players_kmm_2024.util.onError
import com.cjmobileapps.quidditch_players_kmm_2024.util.onSuccess
import com.cjmobileapps.quidditch_players_kmm_2024.util.time.TimeUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class PlayersListViewModelImpl(
    bundle: Bundle?,
    coroutineDispatchers: CoroutineDispatchers,
    private val timeUtil: TimeUtil,
    private val quidditchPlayersUseCase: QuidditchPlayersUseCase,
) : ViewModel(), PlayersListViewModel {
    private val houseName: String = bundle?.getString("houseName") ?: ""

    private val compositeJob = Job()

    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Logger.e(tag) { "coroutineExceptionHandler() error occurred: $throwable \n ${throwable.message} \n ${throwable.printStackTrace()}" }
            snackbarState.value = PlayersListSnackbarState.ShowGenericError()
        }

    private val coroutineContext =
        compositeJob + coroutineDispatchers.main + exceptionHandler + SupervisorJob()

    private val coroutineContextHousesFlow =
        compositeJob + coroutineDispatchers.main + exceptionHandler + SupervisorJob()

    private val coroutineStatusContext =
        compositeJob + coroutineDispatchers.main + exceptionHandler + SupervisorJob()

    private val playersListState = mutableStateOf<PlayersListState>(PlayersListState.LoadingState)

    private val snackbarState =
        mutableStateOf<PlayersListSnackbarState>(
            PlayersListSnackbarState.Idle,
        )

    private val tag = PlayersListViewModelImpl::class.simpleName ?: ""

    override fun getState() = playersListState.value

    override fun getSnackbarState() = snackbarState.value

    init {
        viewModelScope.launch(coroutineContext) {
            quidditchPlayersUseCase
                .fetchPlayersAndPositionsApis(houseName)
                .onError { _, _ ->
                    snackbarState.value = PlayersListSnackbarState.UnableToGetPlayersListError()
                }
        }

        viewModelScope.launch(coroutineContextHousesFlow) {
            quidditchPlayersUseCase.getAllPlayersToDB { playersResponse ->
                playersResponse
                    .onSuccess { players ->
                        playersListState.value =
                            PlayersListState.PlayerListLoadedState(players.toPlayersState())
                        getStatuesForPlayer()
                    }
                    .onError { _, _ ->
                        playersListState.value = PlayersListState.PlayerListLoadedState()
                        snackbarState.value =
                            PlayersListSnackbarState.UnableToGetPlayersListError()
                    }
            }
        }
    }

    private fun getStatuesForPlayer() {
        val state = getState()
        if (state !is PlayersListState.PlayerListLoadedState) return
        coroutineStatusContext.cancelChildren()
        viewModelScope.launch(coroutineStatusContext) {
            while (timeUtil.isDelayLoopRunning()) {
                timeUtil.delayWithRandomTime()
                fetchStatusByHouseName(state.players)
            }
        }
    }

    @VisibleForTesting
    override suspend fun fetchStatusByHouseName(players: List<PlayerState>) {
        quidditchPlayersUseCase.fetchStatusByHouseName(houseName)
            .onSuccess { status ->
                players
                    .find { it.id == status.playerId }
                    ?.status?.value = status.status
            }
            .onError { _, error ->
                Logger.e(tag) { "quidditchPlayersUseCase.fetchStatusByHouseName(houseName) error occurred: $error \\n ${error.message}" }
            }
    }

    override fun getTopBarTitle(): String {
        return houseName
    }

    override fun resetSnackbarState() {
        snackbarState.value = PlayersListSnackbarState.Idle
    }

    override fun resetNavRouteUiToIdle() {
        val state = getState()
        if (state !is PlayersListState.PlayerListLoadedState) return
        state.playersNavRouteUi.value = PlayersListNavRouteUi.Idle
    }

    override fun goToPlayerDetailUi(player: PlayerState) {
        val state = getState()
        if (state !is PlayersListState.PlayerListLoadedState) return
        quidditchPlayersUseCase.currentPlayer = player
        state.playersNavRouteUi.value =
            PlayersListNavRouteUi.GoToPlayerDetailUi(player.id.toString())
    }

    override fun getPlayersListNavRouteUiState(): PlayersListNavRouteUi {
        val state = getState()
        if (state !is PlayersListState.PlayerListLoadedState) return PlayersListNavRouteUi.Idle
        return state.playersNavRouteUi.value
    }

    sealed class PlayersListState {
        data object LoadingState : PlayersListState()

        data class PlayerListLoadedState(
            val players: List<PlayerState> = emptyList(),
            val playersNavRouteUi: MutableState<PlayersListNavRouteUi> =
                mutableStateOf(
                    PlayersListNavRouteUi.Idle,
                ),
        ) : PlayersListState()
    }

    sealed class PlayersListSnackbarState {
        data object Idle : PlayersListSnackbarState()

        data class ShowGenericError(
            val error: String? = null,
        ) : PlayersListSnackbarState()

        data class UnableToGetPlayersListError(
            val error: String? = null,
        ) : PlayersListSnackbarState()
    }

    sealed class PlayersListNavRouteUi {
        data object Idle : PlayersListNavRouteUi()

        data class GoToPlayerDetailUi(val playerId: String) : PlayersListNavRouteUi() {
            fun getNavRouteWithArguments(): String =
                NavItem.PlayerDetail.getNavRouteWithArguments(playerId)
        }
    }
}
