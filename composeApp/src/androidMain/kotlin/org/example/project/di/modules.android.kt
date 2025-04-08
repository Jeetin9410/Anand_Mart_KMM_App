package org.example.project.di


import android.content.SharedPreferences
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import io.ktor.client.engine.okhttp.OkHttp
import org.example.project.config.provideAndroidSettings
import org.example.project.config.provideAndroidSharedPreferences
import org.example.project.utils.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module



actual val platformModule: Module
    get() = module {
        single<SharedPreferences> { provideAndroidSharedPreferences(androidContext()) } // Provide SharedPreferences
        single<Settings> { provideAndroidSettings(get()) }
        single<DatabaseDriverFactory> { DatabaseDriverFactory(androidContext()) }
    }