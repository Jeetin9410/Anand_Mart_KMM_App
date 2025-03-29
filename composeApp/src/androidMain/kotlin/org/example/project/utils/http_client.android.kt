package org.example.project.utils

import android.net.http.HttpResponseCache.install
import io.ktor.client.HttpClient

import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.engine.okhttp.*

actual fun createHttpClient(): HttpClient {
    return HttpClient(OkHttp) {
        install(Logging) {
            level = LogLevel.ALL
        }
    }
}