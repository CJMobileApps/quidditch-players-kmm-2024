package com.cjmobileapps.quidditch_players_kmm_2024.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.PlayerEntity

@Database(entities = [House::class, PlayerEntity::class], version = 1)
@TypeConverters(Converters::class)
@ConstructedBy(QuidditchPlayersDatabaseConstructor::class)
abstract class QuidditchPlayersDatabase : RoomDatabase() {
    abstract fun quidditchPlayersDao(): QuidditchPlayersDao
}

expect class DatabaseFactory

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object QuidditchPlayersDatabaseConstructor : RoomDatabaseConstructor<QuidditchPlayersDatabase> {
    override fun initialize(): QuidditchPlayersDatabase
}
