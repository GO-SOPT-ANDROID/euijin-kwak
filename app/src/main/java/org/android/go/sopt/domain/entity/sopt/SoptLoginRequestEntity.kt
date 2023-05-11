package org.android.go.sopt.domain.entity.sopt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class SoptLoginRequestEntity(
    val id: String,
    val password: String
)
