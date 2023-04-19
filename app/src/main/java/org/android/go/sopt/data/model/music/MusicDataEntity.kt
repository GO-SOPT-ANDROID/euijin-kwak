package org.android.go.sopt.data.model.music

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.android.go.sopt.domain.entity.MusicData

@Entity(tableName = "music")
data class MusicDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val imageUrl:String,
    val musicName:String,
    val singerName:String
)

fun MusicDataEntity.toMusicData() = MusicData(
    id, imageUrl, musicName, singerName
)

fun MusicData.toMusicDataEntity() = MusicDataEntity(
    id, imageUrl, musicName, singerName
)
