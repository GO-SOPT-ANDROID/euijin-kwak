package org.android.go.sopt

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.android.go.sopt.domain.entity.music.MusicData
import org.android.go.sopt.domain.entity.music.SoptGetMusicData
import org.android.go.sopt.domain.entity.music.SoptPostMusicData
import org.android.go.sopt.domain.repository.MusicRepository

class TestMusicRepositoryImpl : MusicRepository {
    override suspend fun getAll(): Flow<List<MusicData>> {
        return flow {
            listOf<MusicData>()
        }
    }
    override suspend fun getLatestMusic(): MusicData {
        return MusicData(0,"", "","")
    }
    override suspend fun insert(musicData: MusicData) = Unit
    override suspend fun delete(musicData: MusicData) = Unit
    override suspend fun update(musicData: MusicData) = Unit

    /**
     * API 테스트 로직
     * **/
    override suspend fun getMusicList(id: String): Result<SoptGetMusicData> {
        return runCatching {
            SoptGetMusicData("API SUCCESS", 200, listOf())
        }
    }

    override suspend fun postMusic(id: String, image: MultipartBody.Part, title: RequestBody, singer: RequestBody): Result<SoptPostMusicData> {
        return runCatching {
            SoptPostMusicData("API SUCCESS", 200, "IU", "너랑 나","https://music.bugs.co.kr/track/2512682")
        }
    }
}