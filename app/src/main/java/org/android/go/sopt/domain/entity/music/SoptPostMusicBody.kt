package org.android.go.sopt.domain.entity.music

import okhttp3.MultipartBody

data class SoptPostMusicBody(
    val singer: String,
    val title: String,
    val image: MultipartBody.Part
)