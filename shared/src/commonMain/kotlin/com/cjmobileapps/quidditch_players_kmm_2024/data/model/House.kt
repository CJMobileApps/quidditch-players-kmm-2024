package com.cjmobileapps.quidditch_players_kmm_2024.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class House(
    @PrimaryKey(autoGenerate = true) val houseId: Int = 0,
    val name: HouseName,
    val imageUrl: String,
    val emoji: String,
)

@Serializable
enum class HouseName {
    GRYFFINDOR,
    SLYTHERIN,
    RAVENCLAW,
    HUFFLEPUFF,
}
