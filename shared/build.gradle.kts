import org.jetbrains.kotlin.konan.target.Family

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    // https://issuetracker.google.com/issues/343408758#comment4
    // https://stackoverflow.com/questions/78627516/room-with-kmm-unresolved-reference-instantiateimpl
    // alias(libs.plugins.room)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).filter { it.konanTarget.family == Family.IOS }
        .forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = "shared"
                isStatic = true
//                with(libs) {
////                    export(bundles.decompose)
////                    export(essenty.lifecycle)
//                }
            }
        }

    applyDefaultHierarchyTemplate()

    sourceSets {
//        val desktopMain by getting

        commonMain.dependencies {
            with(compose) {
                implementation(ui)
                implementation(foundation)
                implementation(material3)
                implementation(runtime)
                implementation(components.resources)

                //todo learning koin wrap this in libs below
            }

            with(libs) {
                api(koin.core)
                implementation(koin.compose)
                implementation(koin.compose.viewmodel)
                implementation(lifecycle.viewmodel)
                implementation(navigation.compose)
                //todo move below up here
            }

            // Ktor for networking
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.content.negotiation)
            implementation(libs.ktor.client.logging) // Logging feature
            implementation(libs.ktor.serialization)

            // JSON serialization
            implementation(libs.kotlinx.serialization.json) // Compatible with Kotlin 1.9.0

            // needed for koin
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.navigation.compose)

            // Kermit Logger
            api(libs.kermit) //Add latest version

            // Image loader
            implementation(libs.kamel.image.default)

            // Room
            implementation(libs.room.runtime)
        }

        androidMain.dependencies {
            api(libs.androidx.activity.compose)

            // Ktor for Android
            implementation(libs.ktor.client.android)

            // learning koin
            api(libs.koin.android)
            api(libs.koin.androidx.compose)

            // Compose preview only works on Android
            implementation("androidx.compose.ui:ui-tooling:1.5.3")
            implementation("androidx.compose.ui:ui-tooling-preview:1.5.3")
        }

        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:2.3.1")
        }
    }
}

android {
    namespace = "com.cjmobileapps.quidditch_players_kmm_2024"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    ksp(libs.room.compiler)

    // room
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}

ksp {
    arg("room.schemaLocation", "${projectDir}/schemas")
}
