package com.cjmobileapps.quidditch_players_kmm_2024.ui.playerslist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cjmobileapps.quidditch_players_kmm_2024.data.MockData
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.toPlayersState
import com.cjmobileapps.quidditch_players_kmm_2024.theme.QuidditchPlayersKMM2024Theme

// TODO this preview not working
@Preview(showBackground = true)
@Composable
fun PlayerListUiPreview() {
    QuidditchPlayersKMM2024Theme {
        val players = MockData.mockRavenclawPlayersEntities.toPlayersState()
        PlayerListUi(modifier = Modifier, players = players) {
        }
    }
}
// TODO this preview not working
@Preview(showBackground = true)
@Composable
fun PlayerCardUiPreview() {
    QuidditchPlayersKMM2024Theme {
        val players = MockData.mockRavenclawPlayersEntities.toPlayersState()
        PlayerCardUi(player = players.first()) { }
    }
}
