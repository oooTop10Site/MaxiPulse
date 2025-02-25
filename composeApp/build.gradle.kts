import org.gradle.kotlin.dsl.compose
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.implementation
import org.gradle.kotlin.dsl.ktorfit
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import kotlin.collections.addAll

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
//    alias(libs.plugins.libres)
}


kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    tasks.withType<JavaExec> {
        jvmArgs = listOf("-Xmx4G")
    }

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val desktopMain by getting
        val wasmJsMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
            implementation("com.squareup.okhttp3:okhttp:4.11.0")
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
//            implementation(libs.libres.compose)

            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
//            implementation("androidx.compose.material:material-icons-extended:1.7.5")
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)


////            Voyager
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.bottomSheetNavigator)
            implementation(libs.voyager.tabNavigator)
            implementation(libs.voyager.transitions)
//            implementation(libs.voyager.koin)
            implementation(libs.voyager.screenModel)

//            Koin
            implementation(libs.koin.core)
            implementation(libs.koin.test)

//            Json Serialization
            implementation(libs.kotlinx.serialization.json)

//            Ktor & Ktorfit
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.auth)
            implementation(libs.jensklingenberg.ktorfit)

//            Orbit Mvi
            implementation(libs.orbit.mvi.core)

            implementation(libs.russhwolf.settings)

            implementation(libs.kotlinx.datetime)


            implementation(libs.coil.compose.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.mp)
            implementation(libs.coil.network.ktor)
//            implementation(libs.coil.network)

//            implementation(libs.konnectivity)

            implementation("dev.chrisbanes.material3:material3-window-size-class-multiplatform:0.5.0")

            implementation("com.mohamedrejeb.dnd:compose-dnd:0.3.0")
        }
//        wasmJsMain.dependencies {
//            implementation("io.ktor:ktor-client-core:2.3.1-wasm0")
//            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.1-wasm0")
//            implementation("io.ktor:ktor-client-content-negotiation:2.3.1-wasm0")
//            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0-RC-wasm0")
//            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.0-RC-wasm0")
//            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1-wasm0")
//            implementation(npm("node-abort-controller", "3.1.1"))
//        }

        desktopMain.dependencies {
            implementation("com.alphacephei:vosk:0.3.38") // Зависимость Vosk
            implementation("net.java.dev.jna:jna:5.12.1")
            implementation("net.java.dev.jna:jna-platform:5.12.1")
            implementation(compose.desktop.currentOs)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    dependencies {
        implementation(libs.koin.core)
        implementation(libs.androidx.activity.compose)
        //    Orbit Mvi
        implementation(libs.orbit.mvi.core)
        implementation(libs.koin.androidx.compose)

//        implementation("com.yandex.android:maps.mobile:4.3.1-lite")
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

dependencies {
    val ktorfitVersion = libs.versions.ktorfit.asProvider().get()
    add("kspCommonMainMetadata", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    add("kspAndroid", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
}

compose.desktop {
    application {
        mainClass = "org.example.project.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.project"
            packageVersion = "1.0.0"
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}


//libres {
//    generatedClassName = "PulseRes" // "Res" by default
//    generateNamedArguments = true // false by default
//    baseLocaleLanguageCode = "ru" // "en" by default
//    camelCaseNamesForAppleFramework = true // false by default
//}
