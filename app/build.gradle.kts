// Раздел plugins подключает плагины, необходимые для сборки Android-приложения
plugins {
    alias(libs.plugins.android.application)
}
//Раздел содержит настройки конфигурации для Android-модуля
android {
    namespace = "com.example.android_2" //Устанавливает пространство имён для кода приложения
    compileSdk = 34 //Указывает версию Android SDK, используемую для компиляции приложения. В данном случае — API 34

    //Этот блок определяет параметры по умолчанию для всех конфигураций сборки
    defaultConfig {
        applicationId = "com.example.android_2" //Уникальный идентификатор приложения
        minSdk = 24 //Минимальная версия Android, на которой может работать приложение (API 24)
        targetSdk = 34 //Версия Android, на которую ориентируется приложение
        versionCode = 1 //Числовая версия приложения для отслеживания обновлений
        versionName = "1.0" //Текстовая версия приложения

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" //Указывает инструментальный тестовый раннер для выполнения тестов
    }

    //Определяет различные типы сборки
    buildTypes {
        // Настройки для сборки релизной версии
        release {
            //Указывает, что обфускация кода (минификация) отключена
            isMinifyEnabled = false
            //Файлы конфигурации ProGuard для обфускации и оптимизации кода
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    //Устанавливает совместимость кода с определёнными версиями Java (в данном случае 1.8)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

//Раздел, где указываются зависимости, необходимые для работы приложения
dependencies {

    //Библиотеки, которые включаются в сборку приложения
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    //Зависимости для юнит-тестов
    testImplementation(libs.junit)
    //Зависимости для инструментальных тестов
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}