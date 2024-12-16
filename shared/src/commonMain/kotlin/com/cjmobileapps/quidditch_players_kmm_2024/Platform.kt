package com.cjmobileapps.quidditch_players_kmm_2024

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform