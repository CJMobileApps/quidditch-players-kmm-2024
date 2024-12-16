package com.cjmobileapps.quidditch_players_kmm_2024

//class AndroidPlatform : Platform {
//    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
//}

actual fun getPlatformName(): String = "Android ${android.os.Build.VERSION.SDK_INT}"