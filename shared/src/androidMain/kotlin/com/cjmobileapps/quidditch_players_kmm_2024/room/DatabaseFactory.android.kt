package com.cjmobileapps.quidditch_players_kmm_2024.room

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

actual class DatabaseFactory {
    companion object {
        fun getDB(context: Context): QuidditchPlayersDatabase {
            val dbFile = context.getDatabasePath("quidditch-players-database.db")
            return Room.databaseBuilder(
                context = context,
                klass = QuidditchPlayersDatabase::class.java,
                name = dbFile.absolutePath,
            ).setDriver(BundledSQLiteDriver())
                .build()
        }
    }
}
