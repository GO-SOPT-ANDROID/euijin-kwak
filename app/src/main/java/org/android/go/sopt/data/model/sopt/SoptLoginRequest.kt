package org.android.go.sopt.data.model.sopt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.go.sopt.domain.entity.sopt.SoptLoginRequestEntity

@Serializable
data class SoptLoginRequest(
    @SerialName("id")
    val id: String?,
    @SerialName("password")
    val password: String?
)

fun SoptLoginRequest.toSoptLoginRequestEntity() = SoptLoginRequestEntity(
    id = id ?: "",
    password = password ?: ""
)

