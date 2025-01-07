package com.cjmobileapps.quidditch_players_kmm_2024.room

import android.content.Context
import androidx.room.Room

actual class DatabaseFactory {
    companion object {
        fun getDB(context: Context): QuidditchPlayersDatabase {
            return Room.databaseBuilder(
                context,
                QuidditchPlayersDatabase::class.java,
                "quidditch-players-database",
            ).build()
        }
    }
}
