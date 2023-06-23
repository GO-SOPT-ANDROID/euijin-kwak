package org.android.go.sopt.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.android.go.sopt.data.dao.MusicDao
import org.android.go.sopt.data.model.music.toMusicData
import org.android.go.sopt.data.model.music.toMusicDataEntity
import org.android.go.sopt.data.model.music.toSoptGetMusicData
import org.android.go.sopt.data.model.music.toSoptPostMusicData
import org.android.go.sopt.data.service.sopt.SoptMusicService
import org.android.go.sopt.domain.entity.music.MusicData
import org.android.go.sopt.domain.entity.music.SoptGetMusicData
import org.android.go.sopt.domain.entity.music.SoptPostMusicData
import org.android.go.sopt.domain.repository.MusicRepository
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val musicDao: MusicDao,
    private val soptMusicService: SoptMusicService
) : MusicRepository {
    override suspend fun getAll(): Flow<List<MusicData>> {
        return musicDao.getAll().map { musicList ->
            musicList.map { it.toMusicData() }
        }
    }

    override suspend fun getLatestMusic(): MusicData {
        return musicDao.getLatestMusic().toMusicData()
    }

    override suspend fun insert(musicData: MusicData) {
        return musicDao.insert(musicData.toMusicDataEntity())
    }

    override suspend fun delete(musicData: MusicData) {
        return musicDao.delete(musicData.toMusicDataEntity())
    }

    override suspend fun update(musicData: MusicData) {
        return musicDao.update(musicData.toMusicDataEntity())
    }

    override suspend fun getMusicList(id: String): Result<SoptGetMusicData> {
        return runCatching { soptMusicService.getMusic(id).toSoptGetMusicData() }
    }

    override suspend fun postMusic(id: String, image: MultipartBody.Part, title: RequestBody, singer: RequestBody): Result<SoptPostMusicData> {
        return runCatching { soptMusicService.postMusic(id, image, title, singer).toSoptPostMusicData() }
    }
}