package com.cjmobileapps.quidditch_players_kmm_2024.di

import com.cjmobileapps.quidditch_players_kmm_2024.di.module.coroutinesModule
import com.cjmobileapps.quidditch_players_kmm_2024.di.module.dataModule
import com.cjmobileapps.quidditch_players_kmm_2024.di.module.databaseModule
import com.cjmobileapps.quidditch_players_kmm_2024.di.module.networkModule
import com.cjmobileapps.quidditch_players_kmm_2024.di.module.uiModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

object InitKoin {

    private var isDebugMode = false

    private var isAndroidDevice = true

    fun intKoin(
        isDebugMode: Boolean,
        isAndroidDevice: Boolean,
        config: KoinAppDeclaration? = null
    ) {
        this.isDebugMode = isDebugMode
        this.isAndroidDevice = isAndroidDevice

        startKoin {
            config?.invoke(this)
            modules(networkModule, coroutinesModule, databaseModule, dataModule, uiModule)
        }
    }

    fun isDebugMode() = isDebugMode

    fun isAndroidDevice() = isAndroidDevice
}
