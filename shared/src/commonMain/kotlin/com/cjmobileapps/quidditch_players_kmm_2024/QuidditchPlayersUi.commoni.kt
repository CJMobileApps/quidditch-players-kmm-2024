package com.cjmobileapps.quidditch_players_kmm_2024

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.cjmobileapps.quidditch_players_kmm_2024.theme.QuidditchPlayersKMM2024Theme
import org.koin.compose.KoinContext

@Composable
fun QuidditchPlayersUi() {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    QuidditchPlayersKMM2024Theme {
        KoinContext {
            Scaffold { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    NavigationGraph(
                        navController = navController,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState,
                    )
                }
            }
        }
    }
}

expect fun getPlatformName(): String
