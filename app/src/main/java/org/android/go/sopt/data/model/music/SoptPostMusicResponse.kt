package org.android.go.sopt.data.model.music


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.go.sopt.domain.entity.music.SoptPostMusicData

@Serializable
data class SoptPostMusicResponse(
    @SerialName("data")
    val data: Data? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("status")
    val status: Int? = null
) {

    @Serializable
    data class Data(
        @SerialName("singer")
        val singer: String? = null,
        @SerialName("title")
        val title: String? = null,
        @SerialName("url")
        val url: String? = null
    )
}

fun SoptPostMusicResponse.toSoptPostMusicData() = SoptPostMusicData(
    message = message.orEmpty(),
    status = status ?: -1,
    singer = data?.singer.orEmpty(),
    title = data?.title.orEmpty(),
    url = data?.url.orEmpty()
)