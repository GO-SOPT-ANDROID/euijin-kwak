package org.android.go.sopt.data.datasource

import kotlinx.coroutines.flow.Flow
import org.android.go.sopt.data.model.kakao.KakaoSearchWebResponse
import org.android.go.sopt.data.model.BaseResult

interface DataSource {
    fun getSearchWeb(keyword: String): Flow<BaseResult<KakaoSearchWebResponse>>
}