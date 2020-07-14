import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Constants.COMPILE_SDK_VERSION)
    buildToolsVersion = Constants.BUILD_TOOLS_VERSION

    defaultConfig {
        applicationId = "com.example.loginapp"
        minSdkVersion(Constants.MIN_SDK_VERSION)
        targetSdkVersion(Constants.TARGET_SDK_VERSION)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            setMinifyEnabled(false)
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
    implementation("androidx.core:core-ktx:1.3.0")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")

    // Dagger
    implementation("com.google.dagger:dagger:${Versions.DAGGER}")
    kapt("com.google.dagger:dagger-compiler:${Versions.DAGGER}")
    implementation("com.google.dagger:dagger-android-support:${Versions.DAGGER}")
    kapt("com.google.dagger:dagger-android-processor:${Versions.DAGGER}")
    // Using Dagger in androidTest and Robolectric too
    kaptAndroidTest("com.google.dagger:dagger-compiler:${Versions.DAGGER}")
    kaptTest("com.google.dagger:dagger-compiler:${Versions.DAGGER}")

    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

}