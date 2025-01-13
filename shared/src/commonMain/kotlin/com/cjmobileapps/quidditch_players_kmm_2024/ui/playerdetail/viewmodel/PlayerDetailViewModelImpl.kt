package com.cjmobileapps.quidditch_players_kmm_2024.ui.playerdetail.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.core.bundle.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerState
import com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditch_players_kmm_2024.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.quidditch_players_kmm_2024.util.onError
import com.cjmobileapps.quidditch_players_kmm_2024.util.onSuccess
import com.cjmobileapps.quidditch_players_kmm_2024.util.time.TimeUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class PlayerDetailViewModelImpl(
    coroutineDispatchers: CoroutineDispatchers,
    bundle: Bundle?,
    private val timeUtil: TimeUtil,
    private val quidditchPlayersUseCase: QuidditchPlayersUseCase,
) : ViewModel(), PlayerDetailViewModel {
    private val playerId: String = bundle?.getString("playerId") ?: ""

    private val compositeJob = Job()

    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Logger.e(tag) { "coroutineExceptionHandler() error occurred: $throwable \n ${throwable.message} ${throwable.printStackTrace()}" }
            snackbarState.value = PlayerDetailSnackbarState.ShowGenericError()
        }

    private val coroutineContextHousesFlow =
        compositeJob + coroutineDispatchers.main + exceptionHandler + SupervisorJob()
    private val playerDetailState =
        mutableStateOf<PlayerDetailState>(PlayerDetailState.LoadingState)

    private val snackbarState =
        mutableStateOf<PlayerDetailSnackbarState>(
            PlayerDetailSnackbarState.Idle,
        )

    private val tag = PlayerDetailViewModelImpl::class.simpleName ?: ""

    override fun getState() = playerDetailState.value

    override fun getSnackbarState() = snackbarState.value

    override fun getTopBarTitle(): String {
        val state = getState()
        if (state !is PlayerDetailState.PlayerDetailLoadedState) return ""
        val player = state.player ?: return ""
        return player.getFullName()
    }

    init {
        val player = quidditchPlayersUseCase.getCurrentPlayer().takeIf { it?.id.toString() == playerId }
        if (player != null) {
            playerDetailState.value = PlayerDetailState.PlayerDetailLoadedState(player = player)
            val state = getState() as PlayerDetailState.PlayerDetailLoadedState
            state.player?.let { playerState ->
                getStatuesForPlayer(playerState)
            }
        } else {
            playerDetailState.value = PlayerDetailState.PlayerDetailLoadedState()
            snackbarState.value = PlayerDetailSnackbarState.UnableToGetPlayerError()
        }
    }

    private fun getStatuesForPlayer(player: PlayerState) {
        coroutineContextHousesFlow.cancelChildren()
        viewModelScope.launch(coroutineContextHousesFlow) {
            val playerId = player.id

            while (timeUtil.isDelayLoopRunning()) {
                timeUtil.delayWithRandomTime()
                quidditchPlayersUseCase.fetchStatusByPlayerId(playerId)
                    .onSuccess { status ->
                        player.status.value = status.status
                    }
                    .onError { _, error ->
                        Logger.e(tag) { "quidditchPlayersUseCase.fetchStatusByPlayerId(playerId) error occurred: $error \\n ${error.message}" }
                    }
            }
        }
    }

    override fun resetSnackbarState() {
        snackbarState.value = PlayerDetailSnackbarState.Idle
    }

    sealed class PlayerDetailState {
        data object LoadingState : PlayerDetailState()

        data class PlayerDetailLoadedState(
            val player: PlayerState? = null,
        ) : PlayerDetailState()
    }

    sealed class PlayerDetailSnackbarState {
        data object Idle : PlayerDetailSnackbarState()

        data class ShowGenericError(
            val error: String? = null,
        ) : PlayerDetailSnackbarState()

        data class UnableToGetPlayerError(
            val error: String? = null,
        ) : PlayerDetailSnackbarState()
    }
}
