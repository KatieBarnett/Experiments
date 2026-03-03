@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "dev.katiebarnett.experiments.kotest"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        applicationId = "dev.katiebarnett.experiments.kotest"
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int
        versionCode = rootProject.extra["appVersionCode"] as Int
        versionName = rootProject.extra["appVersionName"] as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            versionNameSuffix = ".debug"
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    //    testOptions {
//        unitTests.all {
//            useJUnitPlatform()
//            includeAndroidResources = true
//        }
//    }
//    testOptions.unitTests {
//        includeAndroidResources = true
//        returnDefaultValues = true
//        // Comment out when using junit version
//        all {
//            useJUnitPlatform()
//        }
//    }
}
//
//android.testOptions {
//    unitTests.all {
//        logger.error($it)
//        useJUnitPlatform()
//    }
//}

kotlin {
    jvmToolchain(libs.versions.jdkVersion.get().toInt())
}


dependencies {
    implementation(project(":core"))

    implementation(libs.bundles.coreLibs)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.jetpackComposeLibs)
//    implementation 'androidx.test.ext:junit-ktx:1.1.3'
    debugImplementation(libs.bundles.jetpackComposeLibsDebug)
    implementation(libs.bundles.lifecycleLibs)

    testImplementation(libs.bundles.testLibs)
    androidTestImplementation(libs.bundles.androidTestLibs)
}