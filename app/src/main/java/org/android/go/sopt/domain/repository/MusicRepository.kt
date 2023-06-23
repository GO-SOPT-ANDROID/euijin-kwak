package org.android.go.sopt.domain.repository

import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.android.go.sopt.domain.entity.music.MusicData
import org.android.go.sopt.domain.entity.music.SoptGetMusicData
import org.android.go.sopt.domain.entity.music.SoptPostMusicData

interface MusicRepository {

    suspend fun getAll(): Flow<List<MusicData>>

    suspend fun getLatestMusic(): MusicData

    suspend fun insert(musicData: MusicData)

    suspend fun delete(musicData: MusicData)

    suspend fun update(musicData: MusicData)

    suspend fun getMusicList(id: String): Result<SoptGetMusicData>

    suspend fun postMusic(id: String, image: MultipartBody.Part, title: RequestBody, singer: RequestBody): Result<SoptPostMusicData>
}