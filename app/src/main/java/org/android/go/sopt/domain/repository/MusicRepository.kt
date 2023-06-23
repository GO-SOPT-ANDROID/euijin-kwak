package org.android.go.sopt.domain.repository

import kotlinx.coroutines.flow.Flow
import org.android.go.sopt.domain.entity.music.MusicData

interface MusicRepository {

    suspend fun getAll(): Flow<List<MusicData>>

    suspend fun getLatestMusic(): MusicData

    suspend fun insert(musicData: MusicData)

    suspend fun delete(musicData: MusicData)

    suspend fun update(musicData: MusicData)
}