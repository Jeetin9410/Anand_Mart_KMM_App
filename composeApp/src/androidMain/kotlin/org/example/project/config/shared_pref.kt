package org.example.project.config

import android.content.Context
import android.content.SharedPreferences
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

fun provideAndroidSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
}

fun provideAndroidSettings(sharedPreferences: SharedPreferences): Settings {
    return SharedPreferencesSettings(sharedPreferences)
}