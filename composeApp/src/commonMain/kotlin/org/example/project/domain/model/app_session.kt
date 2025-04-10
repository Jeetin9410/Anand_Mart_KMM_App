package org.example.project.domain.model

import com.benasher44.uuid.uuid4
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppSession(
    @SerialName("id")
    public val id: String,

    @SerialName("createAt")
    public val created_at: Long,

    @SerialName("isActive")
    public val is_active: Boolean,
) {
    companion object {
        fun empty() = AppSession(
            id = uuid4().toString(),
            created_at = 0,
            is_active = false
        )
    }
}