package com.cjmobileapps.quidditch_players_kmm_2024.ui.playerdetail.viewmodel

interface PlayerDetailViewModel {
    fun getState(): PlayerDetailViewModelImpl.PlayerDetailState

    fun resetSnackbarState()

    fun getSnackbarState(): PlayerDetailViewModelImpl.PlayerDetailSnackbarState

    fun getTopBarTitle(): String
}
