import org.jetbrains.kotlin.konan.target.Family

//plugins {
//    alias(libs.plugins.kotlinMultiplatform)
//    //alias(libs.plugins.androidLibrary)
//    alias(libs.plugins.androidApplication)
//    alias(libs.plugins.compose.compiler)
//    //id("org.jetbrains.compose")
//    id("org.jetbrains.compose") version "1.5.11" apply false
//
//}
//
//kotlin {
//    androidTarget {
//        compilations.all {
//            kotlinOptions {
//                jvmTarget = "17"
//            }
//        }
//    }
//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach {
//        it.binaries.framework {
//            baseName = "shared"
//            isStatic = true
//        }
//    }
//
//    sourceSets {
//        val commonMain by getting {
//            dependencies {
////                implementation(compose.runtime)
////                implementation(compose.foundation)
////                implementation(compose.material)
////                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
////                implementation(compose.components.resources)
//                //implementation(libs.kotlinx.coroutines.core) // Example of a multiplatform-compatible library
////                implementation(projects.shared)
//
//            }
//        }
//        val androidMain by getting {
//            dependencies {
//                implementation("androidx.compose.runtime:runtime:1.5.4")  // Ensure this is at least version 1.0.0
//
//                //implementation(libs.compose.runtime)  // Add Compose runtime
//                implementation(libs.compose.ui)
//                implementation(libs.compose.ui.tooling.preview)
//                implementation(libs.compose.material3)
//                implementation(libs.androidx.activity.compose)
//                //debugImplementation(libs.compose.ui.tooling)
//            }
//        }
//    }
//
//
//
//    sourceSets {
//        commonMain.dependencies {
//            //put your multiplatform dependencies here
//        }
//        commonTest.dependencies {
//            implementation(libs.kotlin.test)
//        }
//    }
//}
//
//android {
//    namespace = "com.cjmobileapps.quidditch_players_kmm_2024"
//
//    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
//
//
//    compileSdk = 34
//    defaultConfig {
//        minSdk = 26
//    }
//    buildFeatures {
//        compose = true
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_17
//    }
//}

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

//    macosX64 {
//        binaries {
//            executable {
//                entryPoint = "main"
//            }
//        }
//    }
//    macosArm64 {
//        binaries {
//            executable {
//                entryPoint = "main"
//            }
//        }
//    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).filter { it.konanTarget.family == Family.IOS }
        .forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = "shared"
                isStatic = true
                with(libs) {
//                    export(bundles.decompose)
//                    export(essenty.lifecycle)
                }
            }
        }

//    jvm("desktop")
//    js(IR) {
//        browser()
//    }

    applyDefaultHierarchyTemplate()

    /*   cocoapods {
           summary = "Some description for the Shared Module"
           homepage = "Link to the Shared Module homepage"
           version = "1.0"
           ios.deploymentTarget = "14.1"
           podfile = project.file("../iosApp/Podfile")
       }*/

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
                api(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                implementation(libs.lifecycle.viewmodel)
                implementation(libs.navigation.compose)
            }
//
            with(libs) {
//                //implementation(kotlinx.  .serialization.json)
//                implementation(bundles.ktor)
//                api(bundles.decompose)
//                implementation(image.loader)
//                implementation(essenty.lifecycle)
            }

            // Ktor for networking
            implementation("io.ktor:ktor-client-core:2.3.1")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.1")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.1")

            // JSON serialization
            // TODO use gson instead one day
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1") // Compatible with Kotlin 1.9.0

            // needed for koin
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.navigation.compose)

            // Kermit Logger
            api(libs.kermit) //Add latest version

        }

        androidMain.dependencies {
            api(libs.androidx.activity.compose)

            // Ktor for Android
            implementation("io.ktor:ktor-client-android:2.3.1")

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
