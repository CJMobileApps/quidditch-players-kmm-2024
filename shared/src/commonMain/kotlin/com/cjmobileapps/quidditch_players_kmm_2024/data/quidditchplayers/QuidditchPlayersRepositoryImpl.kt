package com.cjmobileapps.quidditch_players_kmm_2024.data.quidditchplayers

import com.cjmobileapps.quidditch_players_kmm_2024.model.BirdImage
import com.cjmobileapps.quidditch_players_kmm_2024.network.QuidditchPlayersApiDataSource

class QuidditchPlayersRepositoryImpl(
    val quidditchPlayersApiDataSource: QuidditchPlayersApiDataSource
) : QuidditchPlayersRepository {

    override suspend fun getImages(): List<BirdImage> {
        return quidditchPlayersApiDataSource.getImages()
    }
}
