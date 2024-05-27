plugins {
    id("swing.android.library")
    id("swing.android.hilt")
    id("swing.android.room")
    id("kotlinx-serialization")
}

android {
    namespace = "com.example.swing.core.data"
}

dependencies {
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:network"))
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.paging.compose)
}