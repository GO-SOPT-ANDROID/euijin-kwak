package org.android.go.sopt.domain.repository

import kotlinx.coroutines.flow.Flow
import org.android.go.sopt.domain.entity.kakao.KakaoSearchWebEntity
import org.android.go.sopt.data.model.BaseResult

interface KakaoRepository {
    fun getSearchWeb(keyword: String): Flow<BaseResult<KakaoSearchWebEntity>>
}