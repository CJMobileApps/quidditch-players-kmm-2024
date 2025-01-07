package com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers

import co.touchlab.kermit.Logger
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerState
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrapper
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.ResponseWrapperUtil
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.toPlayersEntities
import com.cjmobileapps.quidditch_players_kmm_2024.util.onError
import com.cjmobileapps.quidditch_players_kmm_2024.util.onSuccess
import kotlinx.coroutines.flow.collectLatest
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.Error as ErrorResponse

class QuidditchPlayersUseCase(
    private val quidditchPlayersRepository: QuidditchPlayersRepository,
) {
    private val tag = QuidditchPlayersUseCase::class.simpleName ?: ""

    var currentPlayer: PlayerState? = null

    suspend fun getHousesFromDB(onHousesResponse: (ResponseWrapper<List<House>>) -> Unit) {
        try {
            quidditchPlayersRepository.getAllHousesFlow().collectLatest { houses ->
                onHousesResponse(
                    ResponseWrapperUtil.createResponseWrapperSuccess(houses),
                )
            }
        } catch (e: Exception) {
            Logger.e(tag) { "quidditchPlayersRepository.getAllHousesFlow() error occurred: $e \\n ${e.message}" }
            onHousesResponse(
                ResponseWrapperUtil.createResponseWrapperError(
                    ErrorResponse(
                        isError = true,
                        message = e.message
                    ),
                ),
            )
        }
    }

    suspend fun fetchHousesApi(): ResponseWrapper<Boolean> {
        var responseWrapper: ResponseWrapper<Boolean> = ResponseWrapper()

        quidditchPlayersRepository.getAllHouses()
            .onSuccess { houses ->
                responseWrapper =
                    try {
                        quidditchPlayersRepository.createAllHousesToDB(houses)
                        ResponseWrapperUtil.createResponseWrapperSuccess(true)
                    } catch (e: Exception) {
                        Logger.e(tag) { "quidditchPlayersRepository.getAllHouses() error occurred: $e \\n ${e.message}" }
                        ResponseWrapperUtil.createResponseWrapperError(
                            ErrorResponse(
                                isError = true,
                                message = e.message,
                            ),
                        )
                    }
            }
            .onError { statusCode, error ->
                responseWrapper =
                    ResponseWrapperUtil.createResponseWrapperError(
                        error = ErrorResponse(isError = true, message = error.message),
                        statusCode = statusCode,
                    )
            }

        return responseWrapper
    }

    suspend fun fetchPlayersAndPositionsApis(houseName: String): ResponseWrapper<Boolean> {
        var responseWrapper: ResponseWrapper<Boolean> = ResponseWrapper()
        val responseWrappers = quidditchPlayersRepository.fetchPlayersAndPositions(houseName)
        val getPlayersByHouseResponse = responseWrappers.responseWrapper1
        val getPositionsResponse = responseWrappers.responseWrapper2

        getPositionsResponse
            .onSuccess { positions ->
                getPlayersByHouseResponse
                    .onSuccess { players ->
                        responseWrapper =
                            try {
                                quidditchPlayersRepository.createPlayersByHouseToDB(
                                    players.toPlayersEntities(positions),
                                )
                                ResponseWrapperUtil.createResponseWrapperSuccess(true)
                            } catch (e: Exception) {
                                Logger.e(tag) { "quidditchPlayersRepository.createPlayersByHouseToDB() error occurred: $e \\n ${e.message}" }
                                ResponseWrapperUtil.createResponseWrapperError(
                                    ErrorResponse(
                                        isError = true,
                                        message = e.message,
                                    ),
                                )
                            }
                    }
                    .onError { statusCode, error ->
                        responseWrapper =
                            ResponseWrapperUtil.createResponseWrapperError(
                                error = ErrorResponse(isError = true, message = error.message),
                                statusCode = statusCode,
                            )
                    }
            }
            .onError { statusCode, error ->
                responseWrapper =
                    ResponseWrapperUtil.createResponseWrapperError(
                        error = ErrorResponse(isError = true, message = error.message),
                        statusCode = statusCode,
                    )
            }

        return responseWrapper
    }

    suspend fun getAllPlayersToDB(onPlayersResponse: (ResponseWrapper<List<PlayerEntity>>) -> Unit) {
        try {
            quidditchPlayersRepository.getAllPlayersFlow().collectLatest { players ->
                onPlayersResponse(
                    ResponseWrapperUtil.createResponseWrapperSuccess(players),
                )
            }
        } catch (e: Exception) {
            Logger.e(tag) { "quidditchPlayersRepository.getAllPlayersFlow() error occurred: $e \\n ${e.message}" }
            onPlayersResponse(
                ResponseWrapperUtil.createResponseWrapperError(
                    ErrorResponse(isError = true, message = e.message),
                ),
            )
        }
    }

    suspend fun fetchStatusByHouseName(houseName: String) =
        quidditchPlayersRepository.fetchStatusByHouseName(houseName)

    suspend fun fetchStatusByPlayerId(playerId: String) =
        quidditchPlayersRepository.fetchStatusByPlayerId(playerId)
}
