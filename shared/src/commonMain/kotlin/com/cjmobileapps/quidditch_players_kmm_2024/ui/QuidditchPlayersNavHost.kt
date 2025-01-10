package com.cjmobileapps.quidditch_players_kmm_2024.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cjmobileapps.quidditch_players_kmm_2024.ui.houses.HousesUi
import com.cjmobileapps.quidditch_players_kmm_2024.ui.houses.viewmodel.HousesViewModel
import com.cjmobileapps.quidditch_players_kmm_2024.ui.houses.viewmodel.HousesViewModelImpl
import com.cjmobileapps.quidditch_players_kmm_2024.ui.playerdetail.PlayerDetailUi
import com.cjmobileapps.quidditch_players_kmm_2024.ui.playerdetail.viewmodel.PlayerDetailViewModelImpl
import com.cjmobileapps.quidditch_players_kmm_2024.ui.playerslist.PlayersListUi
import com.cjmobileapps.quidditch_players_kmm_2024.ui.playerslist.viewmodel.PlayersListViewModelImpl
import kotlinx.coroutines.CoroutineScope
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

@OptIn(KoinExperimentalAPI::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    NavHost(navController = navController, startDestination = NavItem.Houses.navRoute) {
        composable(NavItem.Houses.navRoute) {
            val housesViewModel: HousesViewModel = koinViewModel<HousesViewModelImpl>()

            HousesUi(
                navController = navController,
                housesViewModel = housesViewModel,
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
            )
        }
        composable(
            NavItem.PlayersList.navRoute,
            arguments = NavItem.PlayersList.arguments,
        ) { backStackEntry ->
            val playersListViewModel: PlayersListViewModelImpl = koinViewModel(
                parameters = { parametersOf(backStackEntry.arguments) }
            )

            PlayersListUi(
                navController = navController,
                playersListViewModel = playersListViewModel,
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
            )
        }
        composable(NavItem.PlayerDetail.navRoute) { backStackEntry ->
            val playerDetailViewModel: PlayerDetailViewModelImpl = koinViewModel(
                parameters = { parametersOf(backStackEntry.arguments) }
            )

            PlayerDetailUi(
                navController = navController,
                coroutineScope = coroutineScope,
                playerDetailViewModel = playerDetailViewModel,
                snackbarHostState = snackbarHostState,
            )
        }
    }
}

sealed class NavItem(
    val navRoute: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {
    data object Houses : NavItem(navRoute = "nav_houses")

    data object PlayersList : NavItem(
        navRoute = "nav_players_list/{houseName}",
        arguments =
        listOf(
            navArgument("houseName") { type = NavType.StringType },
        ),
    ) {
        fun getNavRouteWithArguments(houseName: String): String {
            return "nav_players_list/$houseName"
        }
    }

    data object PlayerDetail : NavItem(
        navRoute = "nav_player_detail/{playerId}",
        arguments =
        listOf(
            navArgument("playerId") { type = NavType.StringType },
        ),
    ) {
        fun getNavRouteWithArguments(playerId: String): String {
            return "nav_player_detail/$playerId"
        }
    }
}
