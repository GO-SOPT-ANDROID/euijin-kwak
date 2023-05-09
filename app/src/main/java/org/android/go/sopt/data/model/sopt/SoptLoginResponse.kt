package org.android.go.sopt.data.model.sopt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SoptLoginResponse(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: SignUpData,
) {
    @Serializable
    data class SignUpData(
        @SerialName("id")
        val Id:String,
        @SerialName("name")
        val name: String,
        @SerialName("skill")
        val skill: String,
    )
}