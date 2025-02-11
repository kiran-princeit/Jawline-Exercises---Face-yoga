plugins {
    id("com.android.application")
}

android {
    namespace = "com.jawline.exercises.faceyoga"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jawline.exercises.faceyoga"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }

//    signingConfigs {
//        create("release") {
//            storeFile = file("D:\\Multiple Email Login\\app\\multipleemaillogin.jks")
//            storePassword = "Prince@9313"
//            keyAlias = "key0"
//            keyPassword = "Prince@9313"
//        }
//    }
//
//    buildTypes {
//        getByName("debug") {
//            manifestPlaceholders["crashlyticsCollectionEnabled"] = "false"
//        }
//        getByName("release") {
//            isMinifyEnabled = true
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//            signingConfig = signingConfigs.getByName("release")
//            manifestPlaceholders["crashlyticsCollectionEnabled"] = "true"
//            ndk {
//                debugSymbolLevel = "FULL"
//            }
//        }
//    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

//    implementation ("com.github.carlosmuvi:SegmentedProgressBar:0.8.3")
    implementation ("com.carlosmuvi.segmentedprogressbar:library:0.2")
    implementation ("androidx.work:work-runtime:2.7.1")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")


    implementation ("nl.bryanderidder:themed-toggle-button-group:1.4.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("nl.dionsegijn:konfetti:1.3.2")
    implementation ("com.github.prolificinteractive:material-calendarview:2.0.0")
    implementation ("com.jakewharton.threetenabp:threetenabp:1.3.1")

    implementation ("androidx.camera:camera-core:1.1.0")
    implementation ("androidx.camera:camera-camera2:1.1.0")
    implementation ("androidx.camera:camera-lifecycle:1.1.0")
    implementation ("androidx.camera:camera-view:1.1.0")
    implementation ("androidx.lifecycle:lifecycle-runtime:2.4.0")
    implementation("com.google.android.exoplayer:exoplayer:2.18.7")

}