package org.example.project

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import platform.Foundation.NSURLRequestCachePolicy

actual fun getPlatformEngine(): HttpClientEngine = Darwin.create {
    configureSession {
        // Enable TLS support
        allowsCellularAccess = true
        timeoutIntervalForRequest = 10.0
        timeoutIntervalForResource = 20.0


        // Enable certificate pinning if needed
        // requestCachePolicy = NSURLRequestCachePolicy.ReloadIgnoringLocalCacheData
    }
}
