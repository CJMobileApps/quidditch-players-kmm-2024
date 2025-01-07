package com.cjmobileapps.quidditch_players_kmm_2024.ui.houses

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cjmobileapps.quidditch_players_kmm_2024.data.MockData
import com.cjmobileapps.quidditch_players_kmm_2024.theme.QuidditchPlayersKMM2024Theme

// TODO preview doesn't work
@Preview(showBackground = true)
@Composable
fun HousesCardUiPreview() {
    QuidditchPlayersKMM2024Theme {
        HouseCardUi(
            house = MockData.mockHouses.first(),
            onCardClick = { },
        )
    }
}

// TODO preview doesn't work
@Preview(showBackground = true)
@Composable
fun HousesGridUiPreview() {
    QuidditchPlayersKMM2024Theme {
        HousesGridUi(
            houses = MockData.mockHouses,
            onCardClick = { },
            modifier = Modifier,
        )
    }
}
