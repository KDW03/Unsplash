plugins {
    id("swing.android.feature")
    id("swing.android.library.compose")
}

android {
    namespace = "com.example.swing.feature.gallery"
}


dependencies {
    implementation(libs.androidx.paging.compose)
}