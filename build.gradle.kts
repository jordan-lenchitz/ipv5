plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    kotlin("multiplatform") version "2.0.21" apply false
    id("com.android.application") version "8.6.1" apply false
    id("com.android.library") version "8.6.1" apply false

    id("org.jetbrains.compose") version "1.7.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
}
