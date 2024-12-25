plugins {
   // kotlin("multiplatform")
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    //alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    //alias(libs.plugins.kotlinMultiplatform)
//    (libs.plugins.kotlinMultiplatform)
//    kotlin("multiplatform") version "2.0.0"
}

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))
            }
        }
    }
}

android {
    namespace = "com.cjmobileapps.quidditch_players_kmm_2024.android"
    sourceSets["main"].manifest.srcFile("src/main/AndroidManifest.xml")

    compileSdk = 34
    defaultConfig {
        applicationId = "com.cjmobileapps.quidditch_players_kmm_2024.android"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
            // IMPORTANT: If testCoverageEnabled and Unit test break you can not see errors
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
