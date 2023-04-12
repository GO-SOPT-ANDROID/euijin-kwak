package org.android.go.sopt.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val id: String,
    val password: String,
    val name: String,
    val specialty: String,
)