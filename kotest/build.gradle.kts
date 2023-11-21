@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "dev.katiebarnett.experiments.kotest"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        applicationId = "dev.katiebarnett.experiments.kotest"
        minSdk = rootProject.extra["wearMinSdk"] as Int
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
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

dependencies {
    implementation(project(":core"))

    implementation(libs.bundles.coreLibs)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.jetpackComposeLibs)
//    implementation 'androidx.test.ext:junit-ktx:1.1.3'
    debugImplementation(libs.bundles.jetpackComposeLibsDebug)
    implementation(libs.bundles.lifecycleLibs)
    
    
    
//    testImplementation 'junit:junit:4.13.2'
//
//    testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4'
//    testImplementation "androidx.arch.core:core-testing:2.1.0"
//
//    testImplementation "androidx.test:core-ktx:1.4.0"
//    testImplementation "androidx.test.ext:junit:1.1.3"
//
//    testImplementation "io.kotest:kotest-runner-junit5:$kotest_version"
//    testImplementation "io.kotest:kotest-assertions-core:$kotest_version"
//    testImplementation "io.kotest:kotest-property:$kotest_version"
//
//    debugImplementation "androidx.compose.ui:ui-test-manifest"
}