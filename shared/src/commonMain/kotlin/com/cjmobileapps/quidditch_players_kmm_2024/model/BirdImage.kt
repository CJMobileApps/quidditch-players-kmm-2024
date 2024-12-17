package com.cjmobileapps.quidditch_players_kmm_2024.model

import kotlinx.serialization.Serializable

@Serializable
data class BirdImage(
    val author: String,
    val category: String,
    val path: String,
)