package com.cjmobileapps.quidditch_players_kmm_2024

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIDevice

actual fun getPlatformName(): String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

