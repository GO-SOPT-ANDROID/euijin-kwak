package org.android.go.sopt.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import org.android.go.sopt.data.datasource.DataSource
import org.android.go.sopt.data.model.kakao.toKakaoSearchWebEntity
import org.android.go.sopt.domain.entity.kakao.KakaoSearchWebEntity
import org.android.go.sopt.domain.repository.KakaoRepository
import org.android.go.sopt.data.model.BaseResult
import org.android.go.sopt.extension.transform
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(private val dataSource: DataSource) : KakaoRepository {
    override fun getSearchWeb(keyword: String): Flow<BaseResult<KakaoSearchWebEntity>> {
        return dataSource.getSearchWeb(keyword).map { response ->
            response.transform { it.toKakaoSearchWebEntity() }
        }
    }
}