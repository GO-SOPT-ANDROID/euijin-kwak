package org.android.go.sopt.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.android.go.sopt.data.model.kakao.toKakaoSearchWebEntity
import org.android.go.sopt.data.service.kakao.KakaoService
import org.android.go.sopt.domain.entity.kakao.KakaoSearchWebEntity
import org.android.go.sopt.domain.repository.KakaoRepository
import org.android.go.sopt.presentation.state.BaseResult
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(private val kakaoService: KakaoService) : KakaoRepository {
    override fun getSearchWeb(keyword: String): Flow<BaseResult<KakaoSearchWebEntity>> = flow {
        val response = kakaoService.getSearchWeb(keyword = keyword)
        if (response.isSuccessful) {
            response.body()?.let {
                emit(BaseResult.Success(it.toKakaoSearchWebEntity()))
            }
        } else {
            emit(BaseResult.Error(response.message()))
        }
    }
}