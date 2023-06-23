package org.android.go.sopt.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.android.go.sopt.data.dao.MusicDao
import org.android.go.sopt.data.model.music.toMusicData
import org.android.go.sopt.data.model.music.toMusicDataEntity
import org.android.go.sopt.domain.repository.MusicRepository
import org.android.go.sopt.domain.entity.music.MusicData
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(private val musicDao: MusicDao) : MusicRepository {
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
}