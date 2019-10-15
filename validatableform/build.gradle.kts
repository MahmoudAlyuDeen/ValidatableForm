plugins {
    id("com.android.library")
    id("com.jfrog.bintray")
    id("com.github.dcendents.android-maven")
    kotlin("android")
}

android {
    compileSdkVersion(29)
    buildToolsVersion = "29.0.2"
    defaultConfig {
        minSdkVersion(14)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    dataBinding {
        isEnabled = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    implementation(Libs.kotlin_stdlib_jdk7)

    // Core + KTX
    implementation(Libs.core_ktx)

    // Lifecycle + Observables
    implementation(Libs.lifecycle_extensions)
}

/**
 * Library Bintray setup. To upload to Bintray, update versions and run bintrayUpload Gradle task.
 */

buildscript {
    extra.apply {
        set("bintrayRepo", "maven")
        set("bintrayName", "com.mahmoudalyudeen.validatableform")

        set("publishedGroupId", "com.github.mahmoudalyudeen")
        set("libraryName", "validatableform")
        set("artifact", "validatableform")

        set("libraryDescription", "Android library for making elegant forms with validation.")

        set("siteUrl", "https://github.com/MahmoudAlyuDeen/validatableform")
        set("gitUrl", "https://github.com/MahmoudAlyuDeen/validatableform.git")

        set("libraryVersion", "1.0.1")
        set("libraryVersionCode", 2)

        set("developerId", "mahmoudalyudeen")
        set("developerName", "Mahmoud AlyuDeen")
        set("developerEmail", "mahmoudalyudeen@gmail.com")

        set("licenseName", "The Apache Software License, Version 2.0")
        set("licenseUrl", "http://www.apache.org/licenses/LICENSE-2.0.txt")
        set("allLicenses", arrayOf("Apache-2.0"))
    }
}

tasks.withType<Javadoc>().all { enabled = false }

apply(from = "https://raw.githubusercontent.com/nuuneoi/JCenter/master/installv1.gradle")
apply(from = "https://raw.githubusercontent.com/nuuneoi/JCenter/master/bintrayv1.gradle")
