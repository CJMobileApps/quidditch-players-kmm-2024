package com.cjmobileapps.quidditch_players_kmm_2024.network

import com.cjmobileapps.quidditch_players_kmm_2024.di.InitKoin
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import co.touchlab.kermit.Logger.Companion as KermitLogger


class KtorHttpClient {
    val httpClient: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        defaultRequest {
            //buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:8080/\"")

            //http://localhost:8080/api/v1/quidditchplayers/position
            host = "10.0.2.2:8080"
//            url {
//                protocol = URLProtocol.HTTPS
//            }
        }
        if (InitKoin.isDebugMode()) {
            //todo undo logger
//            install(Logging) {
//                logger = object : Logger {
//                    override fun log(message: String) {
//                        KermitLogger.d { message }
//                    }
//                }
//                level = LogLevel.ALL
//            }
        }
    }
}

//val httpClient = HttpClient(Android) {
//    defaultRequest {
//        host = "my.zoo.com"
//        url {
//            protocol = URLProtocol.HTTPS
//        }
//    }
//}
//
//val response = httpClient.get<List<CatsResponse>>(
//    path = "animals/cats"
//)
//
//This will call https://my.zoo.com/animals/cats
