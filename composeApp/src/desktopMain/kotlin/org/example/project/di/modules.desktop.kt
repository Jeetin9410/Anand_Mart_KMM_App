package org.example.project.di

import com.russhwolf.settings.Settings
import org.example.project.utils.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module
import com.russhwolf.settings.PreferencesSettings
import java.util.prefs.Preferences

actual val platformModule: Module
    get() = module {
        single<Settings> { PreferencesSettings(Preferences.userRoot().node("AppPrefs")) }

        // Provide a factory to create the DB driver
        single { DatabaseDriverFactory() }
    }