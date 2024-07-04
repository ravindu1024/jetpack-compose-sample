import java.util.Properties


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlinx.serialization)
    id("kotlin-parcelize")
    id("kotlin-kapt")   // This does not work with a version specified - limitation of Gradle
}


android {
    namespace = "com.ravindu1024.newsbrowser"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    val properties = Properties().apply {
        rootProject.file("local.properties").reader().use(::load)
    }

    defaultConfig {
        applicationId = "com.ravindu1024.newsbrowser"
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.version.get()

        buildConfigField("String", "NEWS_API_KEY", "\"${properties["newsapi.key"] as String}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
        freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

}

dependencies {

    // Tests and debug
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.ui.test.manifest)
    debugImplementation(libs.ui.tooling)
    testImplementation(libs.junit)

    // Androidx
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.constraintlayout)
    implementation(libs.androidx.navigation.compose)

    // Glide
    implementation(libs.glide)

    // kotlin
    implementation(libs.kotlinx.serialization)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)            //This version of Hilt does not work with KSP
    implementation(libs.hilt.navigation)

    // Modules
    implementation(project(":domain"))
}