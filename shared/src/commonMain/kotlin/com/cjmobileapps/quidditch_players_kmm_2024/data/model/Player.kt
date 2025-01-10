package com.cjmobileapps.quidditch_players_kmm_2024.data.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: String,
    val firstName: String,
    val lastName: String,
    val yearsPlayed: List<Int>,
    val favoriteSubject: String,
    val position: Int,
    val imageUrl: String,
    val house: HouseName,
)

@Entity
data class PlayerEntity(
    @PrimaryKey val id: String,
    val firstName: String,
    val lastName: String,
    val yearsPlayed: List<Int>,
    val favoriteSubject: String,
    val position: String,
    val imageUrl: String,
    val house: HouseName,
)

data class PlayerState(
    val id: String,
    val firstName: String,
    val lastName: String,
    val yearsPlayed: List<Int>,
    val favoriteSubject: String,
    val position: String,
    val imageUrl: String,
    val house: HouseName,
    val status: MutableState<String> = mutableStateOf(""),
) {
    fun getFullName(): String {
        return "$firstName $lastName"
    }
}

fun PlayerEntity.toPlayerState(): PlayerState {
    return PlayerState(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        yearsPlayed = this.yearsPlayed,
        favoriteSubject = this.favoriteSubject,
        position = this.position,
        imageUrl = this.imageUrl,
        house = this.house,
    )
}

fun List<PlayerEntity>.toPlayersState(): List<PlayerState> {
    return this.map { it.toPlayerState() }
}

fun List<Player>.toPlayersEntities(positions: Map<Int, Position>): List<PlayerEntity> {
    return this.map { it.toPlayerEntity(positions) }
}

fun Player.toPlayerEntity(positions: Map<Int, Position>): PlayerEntity {
    return PlayerEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        yearsPlayed = yearsPlayed,
        favoriteSubject = favoriteSubject,
        position = positions[this.position]?.positionName ?: "",
        imageUrl = imageUrl,
        house = house,
    )
}
