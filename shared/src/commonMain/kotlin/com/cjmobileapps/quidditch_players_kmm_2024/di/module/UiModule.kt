package com.cjmobileapps.quidditch_players_kmm_2024.di.module

import com.cjmobileapps.quidditch_players_kmm_2024.ui.houses.viewmodel.HousesViewModel
import com.cjmobileapps.quidditch_players_kmm_2024.ui.houses.viewmodel.HousesViewModelImpl
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::HousesViewModelImpl).bind<HousesViewModel>()
}
