package org.example.project.data.database

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.example.project.data.db_models.User

object AppDatabase {
    val realm = Realm.open(
        RealmConfiguration.Builder(
            schema = setOf(
                User::class,
            )
        ).name("AnandMartApp").schemaVersion(1).build()
    )
}