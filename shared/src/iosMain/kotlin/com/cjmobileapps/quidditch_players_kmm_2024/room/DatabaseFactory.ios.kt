package com.cjmobileapps.quidditch_players_kmm_2024.room

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSUserDomainMask

//actual class DbClient(
//    private val context: Context
//)

actual class DatabaseFactory {
    companion object {
        fun getDB(): QuidditchPlayersDatabase {
            val dbFilePath = documentDirectory() + "/quidditch-players-database.db"
            return Room.databaseBuilder<QuidditchPlayersDatabase>(
                name = dbFilePath
            ).build()

        }
    }
}


// shared/src/iosMain/kotlin/Database.kt

//fun getDatabaseBuilder(): RoomDatabase.Builder<QuidditchPlayersDatabase> {
//    val dbFilePath = documentDirectory() + "/quidditch-players-database.db"
//    return Room.databaseBuilder<QuidditchPlayersDatabase>(
//        name = dbFilePath
//    )
//}

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