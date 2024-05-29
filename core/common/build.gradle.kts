plugins {
    id("swing.android.library")
    id("swing.android.hilt")
}

android {
    namespace = "com.example.swing.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}