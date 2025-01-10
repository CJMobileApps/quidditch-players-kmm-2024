package com.cjmobileapps.quidditch_players_kmm_2024.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Status(
    val playerId: String,
    val status: String,
)
