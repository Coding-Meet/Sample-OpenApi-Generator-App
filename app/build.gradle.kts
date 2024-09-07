plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.openapi.generator)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.coding.meet.sampleopenapiapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.coding.meet.sampleopenapiapp"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.ktor.client.android)
    implementation(libs.ktor.core)
    implementation(libs.ktor.json)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.negotiation)
    implementation(libs.kotlinx.serialization.json)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

openApiGenerate {
    skipValidateSpec.set(true)
    inputSpec.set("$rootDir/openapi/json-placeholder-api.yaml")
    generatorName.set("kotlin")
    library.set("jvm-ktor")
    packageName.set("com.coding.meet.sampleopenapiapp.code")
    generateApiTests.set(false)
    generateModelTests.set(false)
    configOptions.set(
        mapOf(
            "useCoroutines" to "true",
            "serializationLibrary" to "kotlinx_serialization"
        )
    )

}
// Configure the android source set to include the generated sources
// Start: Uncomment this block if you are directly using generated files in android module.
kotlin{
    sourceSets {
        main{
            kotlin.srcDir("${layout.buildDirectory.get()}/generate-resources/main/src")
        }
    }
}
// End

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    dependsOn("openApiGenerate")
}

tasks.named("openApiGenerate") {
    doLast {

        // Start: Copy Operation: Copies the files from the generated android directory to the actual android source directory in your project.
        // Define source and destination directories for copying generated files
//        val sourceDir = file("${layout.buildDirectory.get()}/generate-resources/main/src/main/kotlin")
//        val destinationDir = file("$projectDir/src/main/java") // Actual android directory
//
//        copy {
//            from(sourceDir) // Source directory (generated)
//            into(destinationDir) // Destination directory (actual android)
//        }
        // End

    }
}
