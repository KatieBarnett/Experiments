@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinAndroid)
    id("androidx.navigation.safeargs.kotlin")
    id("com.apollographql.apollo3").version("3.5.0")
}

android {
    namespace = "dev.katiebarnett.experiments.graphql"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        applicationId = "dev.katiebarnett.experiments.graphql"
        minSdk = rootProject.extra["wearMinSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int
        versionCode = rootProject.extra["appVersionCode"] as Int
        versionName = rootProject.extra["appVersionName"] as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    
    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("io.coil-kt:coil:1.4.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.1")
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.security:security-crypto:1.1.0-alpha03")

    implementation("com.apollographql.apollo3:apollo-runtime:3.5.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

apollo {
    packageName.set("dev.katiebarnett.experiments.graphql.rocketreserver")
}
