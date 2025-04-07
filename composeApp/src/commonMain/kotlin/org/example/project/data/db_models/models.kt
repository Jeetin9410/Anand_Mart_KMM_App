package org.example.project.data.db_models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


class User : RealmObject {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
}