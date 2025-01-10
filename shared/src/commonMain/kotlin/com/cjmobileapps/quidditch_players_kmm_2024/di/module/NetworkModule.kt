package com.cjmobileapps.quidditch_players_kmm_2024.di.module

import com.cjmobileapps.quidditch_players_kmm_2024.network.httpClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> {  httpClient }
}
