package com.cjmobileapps.quidditch_players_kmm_2024.network

import com.cjmobileapps.quidditch_players_kmm_2024.di.InitKoin
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import co.touchlab.kermit.Logger.Companion as KermitLogger


class KtorHttpClient {
    val httpClient: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        if (InitKoin.isDebugMode()) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        KermitLogger.d { message }
                    }
                }
                level = LogLevel.ALL
            }
        }
    }
}
