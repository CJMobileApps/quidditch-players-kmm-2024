package com.cjmobileapps.quidditch_players_kmm_2024.di

import com.cjmobileapps.quidditch_players_kmm_2024.di.module.coroutinesModule
import com.cjmobileapps.quidditch_players_kmm_2024.di.module.dataModule
import com.cjmobileapps.quidditch_players_kmm_2024.di.module.networkModule
import com.cjmobileapps.quidditch_players_kmm_2024.di.module.uiModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

object InitKoin {

    private var isDebugMode = false

    fun intKoin(isDebugMode: Boolean, config: KoinAppDeclaration? = null) {
        this.isDebugMode = isDebugMode

        startKoin {
            config?.invoke(this)
            modules(networkModule, dataModule, uiModule, coroutinesModule)
        }
    }

    fun isDebugMode() = isDebugMode
}
