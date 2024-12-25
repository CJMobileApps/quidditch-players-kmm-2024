package com.cjmobileapps.quidditch_players_kmm_2024.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json


class KtorHttpClient {
    val httpClient: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }
}
