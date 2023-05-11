package org.android.go.sopt.data.repository

import org.android.go.sopt.data.model.kakao.toKakaoSearchWebEntity
import org.android.go.sopt.data.service.kakao.KakaoService
import org.android.go.sopt.domain.entity.kakao.KakaoSearchWebEntity
import org.android.go.sopt.domain.repository.KakaoRepository
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(private val kakaoService: KakaoService) : KakaoRepository {
    override suspend fun getSearchWeb(keyword: String): KakaoSearchWebEntity? {
        val response = kakaoService.getSearchWeb(keyword = keyword)
        return if (response.isSuccessful) {
            return response.body()?.toKakaoSearchWebEntity()
        } else {
            null
        }
    }
}