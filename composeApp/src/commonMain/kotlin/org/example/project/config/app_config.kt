package org.example.project.config

interface AppConfig {
    fun getString(key: String, defaultValue: String = ""): String
    fun putString(key: String, value: String)

    fun getInt(key: String, defaultValue: Int = 0): Int
    fun putInt(key: String, value: Int)

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    fun putBoolean(key: String, value: Boolean)

    /*fun <T> getObject(key: String, defaultValue: T, serializer: kotlinx.serialization.KSerializer<T>): T
    fun <T> putObject(key: String, value: T, serializer: kotlinx.serialization.KSerializer<T>)
*/
}