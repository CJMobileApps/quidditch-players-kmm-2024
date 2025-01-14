import org.jetbrains.kotlin.konan.target.Family

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    // https://mokkery.dev/docs
    alias(libs.plugins.mokkery)
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
        commonTest.dependencies {
            with(libs) {
                // Testing
                implementation(kotlin.test)
                implementation(kotlin.coroutine.test)
                implementation(ktor.client.mock)
            }
        }
        commonMain.dependencies {
            with(compose) {
                implementation(ui)
                implementation(foundation)
                implementation(material3)
                implementation(runtime)
                implementation(components.resources)
            }

            with(libs) {
                // Koin
                api(koin.core)
                implementation(koin.compose)
                implementation(koin.compose.viewmodel)
                implementation(lifecycle.viewmodel)

                // ViewModel
                implementation(lifecycle.viewmodel)

                // Navigation
                implementation(navigation.compose)

                // Ktor for networking
                implementation(ktor.client.core)
                implementation(ktor.content.negotiation)
                implementation(ktor.client.logging) // Logging feature
                implementation(ktor.serialization)

                // JSON serialization
                implementation(kotlinx.serialization.json) // Compatible with Kotlin 1.9.0

                // Kermit Logger
                api(libs.kermit) //Add latest version

                // Image loader
                implementation(kamel.image.default)

                // Room
                implementation(room.runtime)
            }
        }
        androidMain.dependencies {
            // Compose
            api(libs.androidx.activity.compose)

            // Ktor for Android
            implementation(libs.ktor.client.android)

            // Koin
            api(libs.koin.android)
            api(libs.koin.androidx.compose)

            // Compose preview only works on Android
            implementation(libs.androidx.ui.tooling)
            implementation(libs.androidx.ui.tooling.preview)

            // Testing
            implementation("org.mockito.kotlin:mockito-kotlin:5.0.0")
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
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
    add("kspCommonMainMetadata", libs.room.compiler)
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}

ksp {
    arg("room.schemaLocation", "${projectDir}/schemas")
}
