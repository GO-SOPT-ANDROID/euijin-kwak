package org.android.go.sopt.data.model.sopt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.go.sopt.domain.entity.sopt.SoptSignUpResponseEntity

@Serializable
data class SoptSignUpResponse(
    @SerialName("status")
    val status: Int? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("data")
    val data: SignUpData? = null,
) {
    @Serializable
    data class SignUpData(
        @SerialName("name")
        val name: String? = null,
        @SerialName("skill")
        val skill: String? = null,
    )
}

fun SoptSignUpResponse.toSoptSignUpResponseEntity() = SoptSignUpResponseEntity(
    status = status ?: 0,
    message = message ?: "",
    data = SoptSignUpResponseEntity.SignUpData(
        name = data?.name ?: "",
        skill = data?.skill ?: ""
    )
)