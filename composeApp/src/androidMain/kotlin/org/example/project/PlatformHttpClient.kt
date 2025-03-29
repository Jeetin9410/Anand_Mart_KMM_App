package org.example.project


import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

// ✅ Android-specific implementation
actual fun getPlatformEngine(): HttpClientEngine = OkHttp.create()
