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
                implementation(material)
                implementation(runtime)
                implementation(components.resources)
            }

            with(libs) {
//                //implementation(kotlinx.  .serialization.json)
//                implementation(bundles.ktor)
//                api(bundles.decompose)
//                implementation(image.loader)
//                implementation(essenty.lifecycle)
            }
        }

        androidMain {
            dependencies {
//                implementation(libs.androidx.media3.exoplayer)
            }
        }

        //TODO delete this
//        desktopMain.dependencies {
//            implementation(compose.desktop.common)
//            implementation(libs.vlcj)
//        }

        //todo delete this
//        jsMain.dependencies {
//            implementation(compose.html.core)
//            with(libs) {
//                implementation(ktor.client.js)
//                implementation(ktor.client.json.js)
//            }
//        }
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
//    kotlinOptions {
//        jvmTarget = "17"
//    }
    kotlin {
        jvmToolchain(17)
    }
}
