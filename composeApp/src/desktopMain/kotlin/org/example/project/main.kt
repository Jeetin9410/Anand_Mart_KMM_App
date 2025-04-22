package org.example.project

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import org.example.project.di.initKoin
import org.koin.core.context.startKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Anand Mart",
        state = WindowState(size = DpSize(1000.dp, 700.dp))
    ) {
        App()
    }
}