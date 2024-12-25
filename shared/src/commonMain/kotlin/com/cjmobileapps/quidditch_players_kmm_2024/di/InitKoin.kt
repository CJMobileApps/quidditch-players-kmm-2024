package com.cjmobileapps.quidditch_players_kmm_2024.di

import com.cjmobileapps.quidditch_players_kmm_2024.di.module.dataModule
import com.cjmobileapps.quidditch_players_kmm_2024.di.module.networkModule
import com.cjmobileapps.quidditch_players_kmm_2024.di.module.uiModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

object InitKoin {

    fun intKoin(config: KoinAppDeclaration? = null) {
        startKoin {
            config?.invoke(this)
            modules(networkModule, dataModule, uiModule)
        }
    }
}
