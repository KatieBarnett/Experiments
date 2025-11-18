pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://www.jitpack.io") {
            content {
                includeGroup("com.github.androidmads")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://www.jitpack.io") {
            content {
                includeGroup("com.github.androidmads")
            }
        }
    }
}
rootProject.name = "Experiments"
include(":app")
include(":jc-dialoganim")
include(":jc-cardflip")
//include(":graphql")
include(":core")
include(":kotest")
include(":appicon")
include(":jc-top-app-bar")
include(":jc-refresh")
include(":jc-horizontalviewpager")
include(":jc-scrollstate")
include(":jc-text")
include(":jc-modifiers")
include(":jc-previews")
include(":jc-edge-to-edge")
include(":jc-modalbottomsheet")
include(":jc-styled-lines")
include(":jc-lottie")
//include(":jc-screenshot-testing")
