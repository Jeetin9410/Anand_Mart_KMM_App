package org.example.project.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

actual fun createHttpClient(): HttpClient = HttpClient(Android) {
    engine {
        connectTimeout = 10_000
        socketTimeout = 10_000
    }
}