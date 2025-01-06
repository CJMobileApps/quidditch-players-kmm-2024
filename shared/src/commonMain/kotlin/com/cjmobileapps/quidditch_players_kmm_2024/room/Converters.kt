package com.cjmobileapps.quidditch_players_kmm_2024.room

import androidx.room.TypeConverter
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.House
import com.cjmobileapps.quidditch_players_kmm_2024.data.model.HouseName
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun toHouseList(value: String?): List<House> {
        if (value.isNullOrEmpty() || value == "null") return emptyList()
        return Json.decodeFromString<List<House>>(value)
    }

    @TypeConverter
    fun fromHouseList(value: List<House>): String {
        val listSerializer = ListSerializer(House.serializer())
        return Json.encodeToString(listSerializer, value)
    }

    @TypeConverter
    fun toHouseName(value: Int): HouseName {
        return HouseName.entries[value]
    }

    @TypeConverter
    fun fromHouseName(value: HouseName): Int {
        return value.ordinal
    }

    @TypeConverter
    fun toIntList(value: String?): List<Int> {
        if (value.isNullOrEmpty() || value == "null") return emptyList()
        return Json.decodeFromString<List<Int>>(value)
    }


    @TypeConverter
    fun fromIntList(value: List<Int>): String {
        val listSerializer = ListSerializer(Int.serializer())
        return Json.encodeToString(listSerializer, value)
    }
}
