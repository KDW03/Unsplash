plugins {
    id("swing.android.library")
    id("swing.android.hilt")
    id("swing.android.room")
}

android {
    namespace = "com.example.swing.core.database"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.paging.compose)
    implementation(libs.room.paging)
}