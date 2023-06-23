package org.android.go.sopt.data.datasource

import kotlinx.coroutines.flow.Flow
import org.android.go.sopt.data.model.kakao.KakaoSearchWebResponse
import org.android.go.sopt.data.service.kakao.KakaoService
import org.android.go.sopt.data.model.BaseResult
import org.android.go.sopt.util.emitSafe
import javax.inject.Inject

class DataSourceImpl @Inject constructor(private val kakaoService: KakaoService) : DataSource {
    override fun getSearchWeb(keyword: String): Flow<BaseResult<KakaoSearchWebResponse>> = emitSafe{
        kakaoService.getSearchWeb(keyword = keyword)
    }
}