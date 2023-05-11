package org.android.go.sopt.data.model.reqres


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.go.sopt.domain.entity.reqres.ReqresEntity

@Serializable
data class ReqresResponse(
    @SerialName("data")
    val data: List<Data?>?,
    @SerialName("page")
    val page: Int?,
    @SerialName("per_page")
    val perPage: Int?,
    @SerialName("support")
    val support: Support?,
    @SerialName("total")
    val total: Int?,
    @SerialName("total_pages")
    val totalPages: Int?
) {

    @Serializable
    data class Data(
        @SerialName("avatar")
        val avatar: String?,
        @SerialName("email")
        val email: String?,
        @SerialName("first_name")
        val firstName: String?,
        @SerialName("id")
        val id: Int?,
        @SerialName("last_name")
        val lastName: String?
    )

    @Serializable
    data class Support(
        @SerialName("text")
        val text: String?,
        @SerialName("url")
        val url: String?
    )
}

fun ReqresResponse.toReqresEntity(): ReqresEntity {
    return ReqresEntity(
        data = data?.map { data ->
            ReqresEntity.Data(
                avatar = data?.avatar.orEmpty(),
                email = data?.email.orEmpty(),
                firstName = data?.firstName.orEmpty(),
                id = data?.id ?: 0,
                lastName = data?.lastName.orEmpty()
            )
        } ?: emptyList(),
        page = page ?: 0,
        perPage = perPage ?: 0,
        support = ReqresEntity.Support(
            text = support?.text.orEmpty(),
            url = support?.url.orEmpty()
        ),
        total = total ?: 0,
        totalPages = totalPages ?: 0
    )
}