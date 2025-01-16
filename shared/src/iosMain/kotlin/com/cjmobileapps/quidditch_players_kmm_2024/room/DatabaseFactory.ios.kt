package com.cjmobileapps.quidditch_players_kmm_2024.room

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class DatabaseFactory {
    companion object {
        fun getDB(): QuidditchPlayersDatabase {
            val dbFilePath = documentDirectory() + "/quidditch-players-database.db"
            return Room.databaseBuilder<QuidditchPlayersDatabase>(
                name = dbFilePath
            ).setDriver(BundledSQLiteDriver())
                .build()

        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
