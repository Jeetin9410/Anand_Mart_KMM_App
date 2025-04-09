package org.example.project.config


import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import com.russhwolf.settings.serialization.removeValue
import com.russhwolf.settings.set
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer

class AppConfigImpl(private val settings: Settings) : AppConfig {

    override fun getString(key: String, defaultValue: String): String =
        settings.get(key, defaultValue)

    override fun putString(key: String, value: String) {
        settings[key] = value
    }

    override fun getInt(key: String, defaultValue: Int): Int =
        settings.get(key, defaultValue)

    override fun putInt(key: String, value: Int) {
        settings[key] = value
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        settings.get(key, defaultValue)

    override fun putBoolean(key: String, value: Boolean) {
        settings[key] = value
    }

    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    override fun <T> getObject(key: String, serializer: KSerializer<T>): T? =
        settings.decodeValueOrNull(serializer, key)

    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    override fun <T> putObject(key: String, value: T, serializer: KSerializer<T>) {
        settings.encodeValue(serializer, key, value)
    }

    override fun remove(key: String) {
        settings.remove(key)
    }

    override fun <T> removeObject(key: String, serializer: KSerializer<T>) {
        settings.removeValue(serializer, key)
    }

    override fun clearAllData() {
        settings.clear()
    }
}
