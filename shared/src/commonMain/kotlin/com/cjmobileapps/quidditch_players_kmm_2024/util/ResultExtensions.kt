package com.cjmobileapps.quidditch_players_kmm_2024.util

import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Error
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrapper
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrappers
//import com.cjmobileapps.quidditchplayersandroid.data.model.Error
//import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseApiWrapper
//import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
//import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrappers
//import com.google.gson.GsonBuilder
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.withContext
//import retrofit2.HttpException
//import retrofit2.Response
//import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

//inline fun <T : Any> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
//    val body = this.body()
//    if (this.isSuccessful && body != null) action(body)
//    return this
//}
//
//inline fun <T : Any> Response<T>.onError(action: (String?, Int) -> Unit): Response<T> {
//    val errorBody = this.errorBody()
//    if (!this.isSuccessful) {
//        action(errorBody?.string(), this.code())
//    }
//    return this
//}

fun <T : Any> ResponseWrapper<T>.isSuccessful(): Boolean {
    return statusCode in 200..299
}

inline fun <T : Any> ResponseWrapper<T>.onSuccess(action: (T) -> Unit): ResponseWrapper<T> {
    if (data != null && isSuccessful()) {
        action(data)
    }
    return this
}

inline fun <T : Any> ResponseWrapper<T>.onError(action: (statusCode: Int, error: Error) -> Unit): ResponseWrapper<T> {
    if (error != null && !isSuccessful()) {
        action(statusCode, error)
    }
    return this
}

//fun <T : Any> ResponseApiWrapper<T>.responseWrapper(): ResponseWrapper<T> {
//    val response = this.responseWrapper
//    val body = response.body()
//
//    if (response.isSuccessful && body != null) {
//        return body
//    } else {
//        return if (body?.statusCode != null && body.statusCode >= 400 && body.statusCode <= 499) {
//            body
//        } else {
//            Timber.e(response.errorBody()?.string())
//            ResponseWrapper(
//                data = body?.data,
//                error =
//                    Error(
//                        isError = true,
//                        message = "",
//                    ),
//                statusCode = 500,
//            )
//        }
//    }
//}

//fun <T : Any> Response<ResponseWrapper<T>>.responseWrapper(): ResponseWrapper<T> {
//    val body = body()
//
//    if (isSuccessful && body != null) {
//        return body
//    } else {
//        return if (body?.statusCode != null && body.statusCode >= 400 && body.statusCode <= 499) {
//            body
//        } else {
//            Timber.e(errorBody()?.string())
//            ResponseWrapper(
//                data = body?.data,
//                error =
//                    Error(
//                        isError = true,
//                        message = "",
//                    ),
//                statusCode = 500,
//            )
//        }
//    }
//}

suspend fun <T : Any> withContextApiWrapper(
    coroutineContext: CoroutineContext,
    requestFunc: suspend () -> Deferred<ResponseWrapper<T>>,
): ResponseWrapper<T> {
    return withContext(coroutineContext) {
        requestFunc.invoke()
            .await()
    }
}

suspend fun <T : Any> apiWrapper(requestFunc: suspend () -> Deferred<ResponseWrapper<T>>): ResponseWrapper<T> {
    return withContext(coroutineContext) {
        requestFunc.invoke()
            .await()
    }
}

suspend fun <T1 : Any, T2 : Any> withContextApiWrappers(
    coroutineContext: CoroutineContext,
    requestFunc1: suspend () -> Deferred<ResponseWrapper<T1>>,
    requestFunc2: suspend () -> Deferred<ResponseWrapper<T2>>,
): ResponseWrappers<T1, T2> {
    return withContext(coroutineContext) {
        ResponseWrappers(
            responseWrapper1 = apiWrapper(requestFunc1),
            responseWrapper2 = apiWrapper(requestFunc2),
        )
    }
}

//fun <T : Any> defaultErrorResultWrapper(): ResponseWrapper<T> {
//    return ResponseWrapper(
//        data = null,
//        error =
//            Error(
//                isError = true,
//                message = "",
//            ),
//        statusCode = 500,
//    )
//}
