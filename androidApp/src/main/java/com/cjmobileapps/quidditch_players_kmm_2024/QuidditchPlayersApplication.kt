package com.cjmobileapps.quidditch_players_kmm_2024

import android.app.Application
import co.touchlab.kermit.LogcatWriter
import co.touchlab.kermit.Logger
import com.cjmobileapps.quidditch_players_kmm_2024.di.InitKoin
import org.koin.android.ext.koin.androidContext

class QuidditchPlayersApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Logger.setLogWriters(LogcatWriter())
            Logger.d { "${getPlatformName()} logging setup complete" }
        }

        InitKoin.intKoin(isDebugMode = BuildConfig.DEBUG, isAndroidDevice = true) {
            androidContext(this@QuidditchPlayersApplication)
        }
    }
}

fun getPlatformName(): String = "Android ${android.os.Build.VERSION.SDK_INT}"
