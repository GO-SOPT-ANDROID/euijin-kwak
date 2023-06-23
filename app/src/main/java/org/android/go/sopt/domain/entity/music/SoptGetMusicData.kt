package org.android.go.sopt.domain.entity.music


import kotlinx.serialization.Serializable

data class SoptGetMusicData(
    val message: String,
    val status: Int,
    val musicList: List<Music>
) {
    data class Music(
        val singer: String,
        val title: String,
        val url: String
    )
}

