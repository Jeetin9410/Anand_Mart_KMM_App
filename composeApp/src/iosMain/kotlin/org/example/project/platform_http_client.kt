package org.example.project

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import platform.Foundation.NSURLProtocol
import platform.Foundation.NSURLRequestCachePolicy
import platform.Foundation.NSURLSessionConfiguration

actual fun getPlatformEngine(): HttpClientEngine = Darwin.create {
    configureSession {
        allowsCellularAccess = true
        timeoutIntervalForRequest = 10.0
        timeoutIntervalForResource = 20.0

        val config = this as NSURLSessionConfiguration
        config.TLSMinimumSupportedProtocol = 4  // TLS 1.2
        config.TLSMaximumSupportedProtocol = 5
    }
}
