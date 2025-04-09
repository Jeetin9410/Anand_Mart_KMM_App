package org.example.project.utils

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.project.AnandMartDb

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver: SqlDriver = NativeSqliteDriver(
            schema = AnandMartDb.Schema,
            name = "AnandMartDb.db",
        )
        driver.execute(null, "PRAGMA foreign_keys=ON;", 0)
        return driver
    }
}