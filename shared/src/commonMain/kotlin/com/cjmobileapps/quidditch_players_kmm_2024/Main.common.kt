package com.cjmobileapps.quidditch_players_kmm_2024

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cjmobileapps.quidditch_players_kmm_2024.model.BirdImage
import com.cjmobileapps.quidditch_players_kmm_2024.theme.QuidditchPlayersKMM2024Theme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

@Composable
internal fun MainCommon() {
    QuidditchPlayersKMM2024Theme {
        Scaffold { innerPadding ->
            LaunchedEffect(Unit) {
                try {
                    println(getImages())

                } catch (e : Exception) {
                    println("e " + e)
                }
            }
            Box(modifier = Modifier.padding(innerPadding)) {
                Column {
                    Text(
                        text = "hey hey",
                    )
                    Text(
                        text = greet(),
                    )
                }
            }
        }
    }
}

//    private val platform: Platform = getPlatformName()

fun greet(): String {
    return "Hello, ${getPlatformName()}!"
}

val httpClient = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun getImages(): List<BirdImage> {
    val images = httpClient
        .get("https://sebi.io/demo-image-api/pictures.json")
        .body<List<BirdImage>>()

    return images
}

//suspend fun testHttpCall() {
//    try {
//        val response = httpClient.get("https://sebi.io/demo-image-api/pictures.json")
//        println("HTTP Response: ${response.status}")  // Check status code
//        val responseBody = response.body<String>()
//        println("Raw Response: $responseBody")
//    } catch (e: Exception) {
//        println("HTTP Request Error: ${e.message}")
//        e.printStackTrace()
//    }
//}


////TODO move this to Platofrm.kt
//interface Platform {
//    val name: String
//}

expect fun getPlatformName(): String