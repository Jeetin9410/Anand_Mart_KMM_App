package org.example.project.config

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

fun provideIOSUserDefaults(): NSUserDefaults {
    return NSUserDefaults.standardUserDefaults
}

fun provideIOSSettings(userDefaults: NSUserDefaults): Settings {
    return NSUserDefaultsSettings(userDefaults)
}