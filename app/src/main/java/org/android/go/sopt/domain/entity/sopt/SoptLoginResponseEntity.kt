package org.android.go.sopt.domain.entity.sopt


data class SoptLoginResponseEntity(
    val status: Int? = null,
    val message: String? = null,
    val data: SignUpData? = null,
) {

    data class SignUpData(
        val Id: String? = null,
        val name: String? = null,
        val skill: String? = null,
    )
}