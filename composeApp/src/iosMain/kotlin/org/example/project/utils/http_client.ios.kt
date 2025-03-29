package org.example.project.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging

actual fun createHttpClient(): HttpClient  {
    return HttpClient(Darwin.create {
        configureSession {
            // Force TLS 1.2+ (8 = TLSv1.2, 10 = TLSv1.3)
            setTLSMinimumSupportedProtocol(8)
            //setTimeoutInterval(30.0)
        }
    }) {
        install(Logging) {
            level = LogLevel.ALL
        }
    }
}