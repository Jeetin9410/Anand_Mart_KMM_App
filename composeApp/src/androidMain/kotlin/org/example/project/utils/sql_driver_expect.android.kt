package org.example.project.utils

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.project.AnandMartDb

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
                schema = AnandMartDb.Schema,
        context = context,
        name = "AnandMart.db"
        )
    }
}