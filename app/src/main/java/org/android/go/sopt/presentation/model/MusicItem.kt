package org.android.go.sopt.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.go.sopt.domain.entity.MusicData

@Parcelize
data class MusicItem(
    val id: Int = 0,
    val imageUrl: String,
    val musicName: String,
    val singerName: String
) : Parcelable

fun MusicItem.toMusicData() = MusicData(
    id, imageUrl, musicName, singerName
)

fun MusicData.toMusicItem() = MusicItem(
    id, imageUrl, musicName, singerName
)


