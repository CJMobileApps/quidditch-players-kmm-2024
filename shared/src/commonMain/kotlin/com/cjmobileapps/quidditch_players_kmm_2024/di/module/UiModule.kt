package com.cjmobileapps.quidditch_players_kmm_2024.di.module

import com.cjmobileapps.quidditch_players_kmm_2024.ui.houses.viewmodel.HousesViewModel
import com.cjmobileapps.quidditch_players_kmm_2024.ui.houses.viewmodel.HousesViewModelImpl
import com.cjmobileapps.quidditch_players_kmm_2024.ui.playerdetail.viewmodel.PlayerDetailViewModel
import com.cjmobileapps.quidditch_players_kmm_2024.ui.playerdetail.viewmodel.PlayerDetailViewModelImpl
import com.cjmobileapps.quidditch_players_kmm_2024.ui.playerslist.viewmodel.PlayersListViewModel
import com.cjmobileapps.quidditch_players_kmm_2024.ui.playerslist.viewmodel.PlayersListViewModelImpl
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::HousesViewModelImpl).bind<HousesViewModel>()
    viewModelOf(::PlayersListViewModelImpl).bind<PlayersListViewModel>()
    viewModelOf(::PlayerDetailViewModelImpl).bind<PlayerDetailViewModel>()
}
