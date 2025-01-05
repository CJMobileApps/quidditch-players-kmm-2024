package com.cjmobileapps.quidditch_players_kmm_2024.network

import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrapper
import com.cjmobileapps.quidditch_players_kmm_2024.util.withContextApiWrapper
import com.cjmobileapps.quidditch_players_kmm_2024.util.coroutine.CoroutineDispatchers
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class QuidditchPlayersApiDataSource(
    private val ktorHttpClient: KtorHttpClient,
    private val coroutineDispatchers: CoroutineDispatchers,
) {

    suspend fun getAllHouses(): ResponseWrapper<List<House>> {
        return withContextApiWrapper(coroutineContext = coroutineDispatchers.io) {
            coroutineScope {
                async {
                    ktorHttpClient
                        .httpClient
                        .get("api/v1/quidditchplayers/house")
                        .body<ResponseWrapper<List<House>>>()
                }
            }
        }
    }
}

////quidditchPlayersApi.getAllHouses()
//
//}
//
////        //TODO wrap in context api wrapper
////        val httpResponse: HttpResponse = ktorHttpClient.httpClient.get("https://ktor.io/")
////        val byteArrayBody: ByteArray = httpResponse.body()
//
//suspend fun getImages(): List<BirdImage> {
//
//
//    //todo refactor this
//    val images = ktorHttpClient.httpClient
//        .get("https://sebi.io/demo-image-api/pictures.json")
//        .body<List<BirdImage>>()
//
//    return images
//}
//
//suspend fun getImages(): List<BirdImage> = coroutineScope {
//    val deferredImages: Deferred<List<BirdImage>> = async {
//        ktorHttpClient.get("https://sebi.io/demo-image-api/pictures.json").body()
//    }
//
//    // Await the result of the deferred computation
//    deferredImages.await()
//}
//
//suspend fun getPositions() {
//
//}
//
//suspend fun getImages(): List<BirdImage> = coroutineScope {
//    val deferredImages: Deferred<List<BirdImage>> = async(Dispatchers.IO) {
//        // Perform the HTTP call in an I/O-optimized thread
//        ktorHttpClient.get("https://sebi.io/demo-image-api/pictures.json").body()
//    }
//
//    // Await the result of the deferred computation
//    deferredImages.await()
//}
//
////http://localhost:8080/api/v1/quidditchplayers/position
//
//class QuidditchPlayersApiDataSource(
//    private val quidditchPlayersApi: QuidditchPlayersApi,
//    private val coroutineDispatchers: CoroutineDispatchers,
//) {
//    suspend fun getAllHouses(): ResponseWrapper<List<House>> {
//        return withContextApiWrapper(coroutineDispatchers.io) {
//            quidditchPlayersApi.getAllHouses()
//        }
//    }


//}


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
