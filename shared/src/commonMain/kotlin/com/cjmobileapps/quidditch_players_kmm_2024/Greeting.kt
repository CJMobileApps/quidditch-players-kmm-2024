package com.cjmobileapps.quidditch_players_kmm_2024

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}