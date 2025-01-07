package com.cjmobileapps.quidditch_players_kmm_2024.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity

@Database(entities = [House::class, PlayerEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class QuidditchPlayersDatabase : RoomDatabase() {
    abstract fun quidditchPlayersDao(): QuidditchPlayersDao
}

expect class DatabaseFactory
