package org.example.project.utils

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.project.AnandMartDb

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = AnandMartDb.Schema,
            name = "AnandMartDb.db"
        )
    }
}