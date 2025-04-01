package org.example.project.di

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.example.project.config.provideIOSSettings
import org.example.project.config.provideIOSUserDefaults
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSURLSessionConfiguration
import platform.Foundation.NSUserDefaults

actual val platformModule: Module
    get() = module {
        single<NSUserDefaults> { provideIOSUserDefaults() } // Provide NSUserDefaults
        single<Settings> { provideIOSSettings(get()) }
    }