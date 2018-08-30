import com.android.build.gradle.internal.api.ApkVariantOutputImpl

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(27)
    defaultConfig {
        applicationId = "com.kotlin.dsl.gradle.sample"
        minSdkVersion(16)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("kotlindslgradle.keystore")
            storePassword = "12345678"
            keyAlias = "kotlin"
            keyPassword = "12345678"
        }
        getByName("debug") {
            storeFile = file("kotlindslgradle.keystore")
            storePassword = "12345678"
            keyAlias = "kotlin"
            keyPassword = "12345678"
        }
    }

    buildTypes {
        getByName("release") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    packagingOptions {
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")
    }

    android.applicationVariants.all {
        outputs.all {
            if (this is ApkVariantOutputImpl) {
                this.outputFileName = "$versionCode@app_$versionName.apk"
            }
        }
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    val supportVersion = "27.1.1"
    implementation("com.android.support:appcompat-v7:$supportVersion")
    implementation("com.android.support:support-v4:$supportVersion")
    implementation("com.android.support:gridlayout-v7:$supportVersion")
    implementation("com.android.support:recyclerview-v7:$supportVersion")
    implementation("com.android.support:design:$supportVersion")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.2.61")

    testImplementation("junit:junit:4.12")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
}