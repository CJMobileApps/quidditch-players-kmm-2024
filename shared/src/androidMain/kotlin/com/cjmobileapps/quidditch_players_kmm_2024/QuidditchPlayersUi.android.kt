package com.cjmobileapps.quidditch_players_kmm_2024

import androidx.compose.runtime.Composable

@Composable
fun MainAndroid() {
    QuidditchPlayersUi()
}

actual fun getPlatformName(): String = "Android ${android.os.Build.VERSION.SDK_INT}"
