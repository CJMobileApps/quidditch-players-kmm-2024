package com.cjmobileapps.quidditch_players_kmm_2024.network

import com.cjmobileapps.quidditch_players_kmm_2024.model.BirdImage
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class QuidditchPlayersApiDataSource(private val ktorHttpClient : KtorHttpClient) {

//    val httpClient: HttpClient = HttpClient {
//        install(ContentNegotiation) {
//            json()
//        }
//    }

    suspend fun getImages(): List<BirdImage> {
        //TODO wrap in context api wrapper
//        val httpResponse: HttpResponse = ktorHttpClient.httpClient.get("https://ktor.io/")
//        val byteArrayBody: ByteArray = httpResponse.body()

        //todo refactor this
        val images = ktorHttpClient.httpClient
            .get("https://sebi.io/demo-image-api/pictures.json")
            .body<List<BirdImage>>()

        return images
    }
}


//runBlocking {
//    // Parallel requests
//    val firstRequest: Deferred<String> = async { client.get("http://localhost:8080/path1").bodyAsText() }
//    val secondRequest: Deferred<String> = async { client.get("http://localhost:8080/path2").bodyAsText() }
//    val firstRequestContent = firstRequest.await()
//    val secondRequestContent = secondRequest.await()
//}


//import io.ktor.client.*
//import io.ktor.client.call.*
//import io.ktor.client.request.*
//import io.ktor.http.*
//
//val httpResponse: HttpResponse = client.get("https://ktor.io/")
//if (httpResponse.status.value in 200..299) {
//    println("Successful response!")
//}


//val httpResponse: HttpResponse = client.get("https://ktor.io/")
//val byteArrayBody: ByteArray = httpResponse.body()


//val client = HttpClient {
//    HttpResponseValidator {
//        validateResponse { response ->
//            if (response.status != HttpStatusCode.OK) {
//                throw ResponseException(response, "HTTP status is not OK: ${response.status}")
//            }
//        }
//    }
//}
//
//try {
//    val body = client.get("http://localhost:8080/invalid-path").bodyAsText()
//    println(body)
//} catch (e: ResponseException) {
//    println("Request failed with status: ${e.response.status}")
//}


//val response: HttpResponse = client.get("http://localhost:8080/invalid-path")
//if (response.status == HttpStatusCode.NotFound) {
//    println("404 Not Found")
//    val errorBody = response.bodyAsText() // Could be empty or contain error details.
//    println("Error body: $errorBody")
//}
