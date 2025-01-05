package com.cjmobileapps.quidditch_players_kmm_2024.data.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Status(
    val playerId: Uuid,
    val status: String,
)
