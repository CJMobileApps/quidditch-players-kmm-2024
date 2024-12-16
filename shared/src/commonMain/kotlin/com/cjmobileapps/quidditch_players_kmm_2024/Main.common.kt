package com.cjmobileapps.quidditch_players_kmm_2024

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun MainCommon() {
    MaterialTheme {
        Text(
            text = "hey hey",
        )
        Text(
            text = greet(),
        )

    }
}

//    private val platform: Platform = getPlatformName()

fun greet(): String {
    return "Hello, ${getPlatformName()}!"
}

////TODO move this to Platofrm.kt
//interface Platform {
//    val name: String
//}

expect fun getPlatformName(): String