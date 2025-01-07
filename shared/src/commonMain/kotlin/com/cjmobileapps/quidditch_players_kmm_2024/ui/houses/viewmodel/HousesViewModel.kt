package com.cjmobileapps.quidditch_players_kmm_2024.ui.houses.viewmodel

interface HousesViewModel {
    fun getState(): HousesViewModelImpl.HousesState

    fun getSnackbarState(): HousesViewModelImpl.HousesSnackbarState

    fun getHousesNavRouteUiState(): HousesViewModelImpl.HousesNavRouteUi

    fun resetSnackbarState()

    fun resetNavRouteUiToIdle()

    fun goToPlayersListUi(houseName: String)
}
