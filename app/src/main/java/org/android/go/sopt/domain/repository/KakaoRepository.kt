package org.android.go.sopt.domain.repository

import org.android.go.sopt.domain.entity.kakao.KakaoSearchWebEntity

interface KakaoRepository {
    suspend fun getSearchWeb(keyword: String): KakaoSearchWebEntity?
}