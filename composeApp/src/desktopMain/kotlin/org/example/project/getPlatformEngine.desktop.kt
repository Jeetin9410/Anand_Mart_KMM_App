package org.example.project

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO

actual fun getPlatformEngine(): HttpClientEngine = CIO.create()