package org.example.project


import io.ktor.client.engine.*
import io.ktor.client.engine.android.*

// ✅ Android-specific implementation
actual fun getPlatformEngine(): HttpClientEngine = Android.create()
