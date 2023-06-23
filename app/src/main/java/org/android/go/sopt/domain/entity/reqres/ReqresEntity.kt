package org.android.go.sopt.domain.entity.reqres

data class ReqresEntity(
    val data: List<Data>,
    val page: Int,
    val perPage: Int,
    val support: Support,
    val total: Int,
    val totalPages: Int
) {
    data class Support(
        val text: String,
        val url: String
    )

    data class Data(
        val avatar: String,
        val email: String,
        val firstName: String,
        val id: Int,
        val lastName: String
    )
}

