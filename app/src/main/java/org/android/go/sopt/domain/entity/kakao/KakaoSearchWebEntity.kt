package org.android.go.sopt.domain.entity.kakao

data class KakaoSearchWebEntity(
    val documents: List<Document?>?,
    val meta: Meta?
) {
    data class Document(
        val contents: String?,
        val datetime: String?,
        val title: String?,
        val url: String?
    )

    data class Meta(
        val isEnd: Boolean?,
        val pageableCount: Int?,
        val totalCount: Int?
    )
}