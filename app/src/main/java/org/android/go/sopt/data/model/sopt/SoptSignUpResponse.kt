package org.android.go.sopt.data.model.sopt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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