package org.android.go.sopt.data.model.kakao


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.go.sopt.domain.entity.kakao.KakaoSearchWebEntity

@Serializable
data class KakaoSearchWebResponse(
    @SerialName("documents")
    val documents: List<Document?>? = null,
    @SerialName("meta")
    val meta: Meta? = null
) {
    @Serializable
    data class Document(
        @SerialName("contents")
        val contents: String? = null,
        @SerialName("datetime")
        val datetime: String? = null,
        @SerialName("title")
        val title: String? = null,
        @SerialName("url")
        val url: String? = null
    )

    @Serializable
    data class Meta(
        @SerialName("is_end")
        val isEnd: Boolean? = null,
        @SerialName("pageable_count")
        val pageableCount: Int? = null,
        @SerialName("total_count")
        val totalCount: Int? = null
    )
}

fun KakaoSearchWebResponse.toKakaoSearchWebEntity() = KakaoSearchWebEntity(
    documents = documents?.map {
        KakaoSearchWebEntity.Document(
            contents = it?.contents ?: "",
            it?.datetime ?: "",
            it?.title ?: "",
            it?.url ?: ""
        )
    } ?: emptyList(),
    meta = KakaoSearchWebEntity.Meta(
        meta?.isEnd ?: false,
        meta?.pageableCount ?: 0,
        meta?.totalCount ?: 0
    )
)
