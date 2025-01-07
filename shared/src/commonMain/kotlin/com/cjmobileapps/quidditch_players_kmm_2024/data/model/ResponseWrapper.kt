package com.cjmobileapps.quidditch_players_kmm_2024.data.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
data class ResponseWrapper<T>(
    val data: T? = null,
    val error: Error? = null,
    val statusCode: Int = 999,
)

@Serializable
data class Error(
    val isError: Boolean = false,
    val message: String? = null,
)

data class ResponseWrappers<T1, T2>(
    val responseWrapper1: ResponseWrapper<T1>,
    val responseWrapper2: ResponseWrapper<T2>,
)

object ResponseWrapperUtil {
    fun <T> createResponseWrapperSuccess(data: T) = ResponseWrapper(data = data, statusCode = HttpStatusCode.OK.value)

    fun <T> createResponseWrapperError(
        error: Error,
        statusCode: Int = HttpStatusCode.BadRequest.value,
    ) = ResponseWrapper<T>(error = error, statusCode = statusCode)
}
