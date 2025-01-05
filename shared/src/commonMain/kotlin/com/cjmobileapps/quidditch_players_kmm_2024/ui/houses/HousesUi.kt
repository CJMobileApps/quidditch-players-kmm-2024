package com.cjmobileapps.quidditch_players_kmm_2024.ui.houses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.theme.QuidditchPlayersKMM2024Theme
import com.cjmobileapps.quidditch_players_kmm_2024.ui.houses.viewmodel.HousesViewModel
import com.cjmobileapps.quidditch_players_kmm_2024.ui.util.QuidditchPlayersImage
import com.cjmobileapps.quidditch_players_kmm_2024.ui.houses.viewmodel.HousesViewModelImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import quidditch_players_kmm_2024.shared.generated.resources.Res
import quidditch_players_kmm_2024.shared.generated.resources.some_error_occurred
import quidditch_players_kmm_2024.shared.generated.resources.unable_to_get_houses

@Composable
fun HousesUi(
    navController: NavController,
    housesViewModel: HousesViewModel,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        topBar = {
            //QuidditchPlayersTopAppBar(navController)
                 },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
            when (val state = housesViewModel.getState()) {
                is HousesViewModelImpl.HousesState.LoadingState -> {
                    HousesShimmerLoadingUi(modifier = Modifier.padding(innerPadding))
                }

                is HousesViewModelImpl.HousesState.HousesLoadedState -> {
                    HousesLoadedUi(
                        modifier = Modifier.padding(innerPadding),
                        housesViewModel = housesViewModel,
                        housesLoadedState = state,
                        navController = navController,
                    )
                }
            }

        val snackbarMessage: String? =
            when (val state = housesViewModel.getSnackbarState()) {
                is HousesViewModelImpl.HousesSnackbarState.Idle -> null

                is HousesViewModelImpl.HousesSnackbarState.ShowGenericError ->
                    state.error ?: stringResource(Res.string.some_error_occurred)

                is HousesViewModelImpl.HousesSnackbarState.UnableToGetHousesListError ->
                    stringResource(Res.string.unable_to_get_houses)
            }

        if (snackbarMessage != null) {
            HousesSnackbar(
                message = snackbarMessage,
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
                housesViewModel = housesViewModel,
            )
        }
    }
}

@Composable
fun HousesSnackbar(
    message: String,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    housesViewModel: HousesViewModel,
) {
    LaunchedEffect(key1 = message) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message = message)
            housesViewModel.resetSnackbarState()
        }
    }
}

@Composable
fun HousesLoadedUi(
    modifier: Modifier,
    housesViewModel: HousesViewModel,
    housesLoadedState: HousesViewModelImpl.HousesState.HousesLoadedState,
    navController: NavController,
) {
    val houses = housesLoadedState.houses

    HousesGridUi(
        houses = houses,
        onCardClick = { houseName ->
            housesViewModel.goToPlayersListUi(houseName)
        },
        modifier = modifier,
    )

    when (val navigateRouteUiValue = housesViewModel.getHousesNavRouteUiState()) {
        is HousesViewModelImpl.HousesNavRouteUi.Idle -> {}
        is HousesViewModelImpl.HousesNavRouteUi.GoToPlayerListUi -> {
            navController.navigate(navigateRouteUiValue.getNavRouteWithArguments())
            housesViewModel.resetNavRouteUiToIdle()
        }
    }
}

@Composable
fun HouseCardUi(
    onCardClick: (houseName: String) -> Unit,
    house: House,
) {
    ElevatedCard(
        modifier =
        Modifier
            .fillMaxWidth()
            .clickable { onCardClick.invoke(house.name.name) },
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            QuidditchPlayersImage(
                modifier =
                Modifier
                    .size(160.dp)
                    .fillMaxWidth(),
                imageUrl = house.imageUrl,
                contentDescription = house.name.name,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier.wrapContentWidth(),
                    textAlign = TextAlign.Center,
                    text = house.name.name,
                )

                Text(
                    modifier =
                    Modifier
                        .wrapContentWidth()
                        .padding(start = 4.dp),
                    textAlign = TextAlign.Center,
                    text = house.emoji,
                )
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun HousesCardUiPreview() {
    QuidditchPlayersKMM2024Theme {
//        HouseCardUi(
//            house = MockData.mockHouses.first(),
//            onCardClick = { },
//        )
    }
}

@Composable
fun HousesGridUi(
    houses: List<House>,
    onCardClick: (houseName: String) -> Unit,
    modifier: Modifier,
) {
    LazyVerticalGrid(
        modifier = modifier.padding(16.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(houses) { house ->
            HouseCardUi(
                house = house,
                onCardClick = onCardClick,
            )
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun HousesGridUiPreview() {
    QuidditchPlayersKMM2024Theme {
//        HousesGridUi(
//            houses = MockData.mockHouses,
//            onCardClick = { },
//            modifier = Modifier,
//        )
    }
}