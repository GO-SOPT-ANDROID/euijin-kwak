package org.android.go.sopt.data.model.sopt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.go.sopt.domain.entity.sopt.SoptLoginResponseEntity

@Serializable
data class SoptLoginResponse(
    @SerialName("status")
    val status: Int? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("data")
    val data: SignUpData? = null,
) {
    @Serializable
    data class SignUpData(
        @SerialName("id")
        val Id: String? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("skill")
        val skill: String? = null,
    )
}

fun SoptLoginResponse.toSoptLoginResponseEntity() = SoptLoginResponseEntity(
    status = status ?: 0,
    message = message ?: "",
    data = SoptLoginResponseEntity.SignUpData(
        Id = data?.Id ?: "",
        name = data?.name ?: "",
        skill = data?.skill ?: ""
    )
)