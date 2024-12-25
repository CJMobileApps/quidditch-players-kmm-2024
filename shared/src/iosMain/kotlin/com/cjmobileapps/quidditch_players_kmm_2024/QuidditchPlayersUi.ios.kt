package com.cjmobileapps.quidditch_players_kmm_2024

import androidx.compose.ui.window.ComposeUIViewController
import co.touchlab.kermit.Logger
import co.touchlab.kermit.NSLogWriter
import com.cjmobileapps.quidditch_players_kmm_2024.di.InitKoin
import platform.UIKit.UIDevice

@Suppress("unused")
fun MainViewController() = ComposeUIViewController(
    configure = {
        setupLogging()
        InitKoin.intKoin()
    }
) {
    QuidditchPlayersUi()
}


fun setupLogging() {
    Logger.setLogWriters(NSLogWriter())
    Logger.d { "${getPlatformName()} logging setup complete" }
}

actual fun getPlatformName(): String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
