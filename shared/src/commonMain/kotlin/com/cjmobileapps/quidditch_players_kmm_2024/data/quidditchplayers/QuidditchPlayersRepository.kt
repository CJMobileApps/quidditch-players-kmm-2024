package com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers

import com.cjmobileapps.quidditch_players_kmm_2024.model.BirdImage

interface QuidditchPlayersRepository {

    suspend fun getImages(): List<BirdImage>
}
