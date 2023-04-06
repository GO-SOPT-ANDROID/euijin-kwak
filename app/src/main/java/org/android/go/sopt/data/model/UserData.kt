package org.android.go.sopt.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.go.sopt.domain.repository.UserEntity

@Parcelize
data class UserData(
    val id: String?,
    val password: String?,
    val name: String?,
    val specialty: String?
) : Parcelable

fun UserEntity.toUserData() = UserData(
    id = id,
    password = password,
    name = name,
    specialty = specialty
)

fun UserData.toUserEntity() = UserEntity(
    id = id ?: "-1",
    password = password ?: "-1",
    name = name ?: "",
    specialty = specialty ?: ""
)

