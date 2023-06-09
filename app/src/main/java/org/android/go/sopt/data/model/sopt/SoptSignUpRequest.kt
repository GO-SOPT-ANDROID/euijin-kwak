package org.android.go.sopt.data.model.sopt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.go.sopt.domain.entity.sopt.SoptSignUpRequestEntity

@Serializable
data class SoptSignUpRequest(
    @SerialName("id")
    val id: String,
    @SerialName("password")
    val password: String,
    @SerialName("name")
    val name: String,
    @SerialName("skill")
    val skill: String,
)

fun SoptSignUpRequest.toSoptSignUpRequestEntity() = SoptSignUpRequestEntity(
    id = id ?: "",
    password = password ?: "",
    name = name ?: "",
    skill = skill ?: ""
)

fun SoptSignUpRequestEntity.toSoptSignUpRequest() = SoptSignUpRequest(
    id = id ?: "",
    password = password ?: "",
    name = name ?: "",
    skill = skill ?: ""
)
