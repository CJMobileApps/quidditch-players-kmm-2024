package com.cjmobileapps.quidditch_players_kmm_2024.ui.playerdetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cjmobileapps.quidditch_players_kmm_2024.data.MockData
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.toPlayersState
import com.cjmobileapps.quidditch_players_kmm_2024.theme.QuidditchPlayersKMM2024Theme

@Preview(showBackground = true)
@Composable
fun PlayerDetailPreview() {
    QuidditchPlayersKMM2024Theme {
        val players = MockData.mockPlayersEntities.toPlayersState()
        PlayerDetail(
            modifier = Modifier,
            player = players.first(),
        )
    }
}
