package com.cjmobileapps.quidditch_players_kmm_2024.data.model

data class House(
    val houseId: Int = 0,
    val name: HouseName,
    val imageUrl: String,
    val emoji: String,
)

enum class HouseName {
    GRYFFINDOR,
    SLYTHERIN,
    RAVENCLAW,
    HUFFLEPUFF,
}
