plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34
//    packaging {
//        // for JNA and JNA-platform
//        resources.excludes.add("META-INF/AL2.0")
//        resources.excludes.add("META-INF/LGPL2.1")
//        // for byte-buddy
//        resources.excludes.add("META-INF/licenses/ASM")
//        resources.pickFirsts.add("win32-x86-64/attach_hotspot_windows.dll")
//        resources.pickFirsts.add("win32-x86/attach_hotspot_windows.dll")
//    }
    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        buildConfig = true
        compose = true
        viewBinding = true
        //noinspection DataBindingWithoutKapt
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    buildTypes {
        release {
            buildConfigField("String", "SERVER_CLIENT_ID", "\"1098126475180-pe6l2occ7vlj8e3dpps92h71dai4c62u.apps.googleusercontent.com\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "SERVER_CLIENT_ID", "\"1098126475180-pe6l2occ7vlj8e3dpps92h71dai4c62u.apps.googleusercontent.com\"")
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    sourceSets {
        getByName("main").java.srcDirs("build/generated/source/navigation-args")
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.compose.v240rc01)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.credentials.v130alpha01)
    implementation(libs.androidx.credentials.play.services.auth.v130alpha01)
    implementation(libs.googleid.v110)
    implementation(libs.play.services.maps)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.picasso)
    implementation(libs.jackson.core.v2171)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.databind)
    implementation(libs.ccp)
    implementation(libs.androidx.constraintlayout)
    //implementation(libs.interactive.info.window.android)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}