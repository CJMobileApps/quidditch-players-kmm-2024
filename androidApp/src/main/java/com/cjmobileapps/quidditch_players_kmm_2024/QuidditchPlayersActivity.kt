package com.cjmobileapps.quidditch_players_kmm_2024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class QuidditchPlayersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainAndroid()
        }
    }
}
