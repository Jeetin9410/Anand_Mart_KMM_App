package org.example.project.config


import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.serialization.json.Json
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

    /*override fun <T> getObject(key: String, defaultValue: T, serializer: KSerializer<T>): T {
        val jsonString = settings.get<String>(key, "")
        return if (jsonString.isNotEmpty()) {
            Json.decodeFromString(serializer, jsonString)
        } else {
            defaultValue
        }
    }

    override fun <T> putObject(key: String, value: T, serializer: KSerializer<T>) {
        val jsonString = Json.encodeToString(serializer, value)
        settings[key] = jsonString
    }*/
}
