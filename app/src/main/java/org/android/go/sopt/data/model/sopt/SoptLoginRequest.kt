package org.android.go.sopt.data.model.sopt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SoptLoginRequest(
    @SerialName("id")
    val id: String,
    @SerialName("password")
    val password: String
)
