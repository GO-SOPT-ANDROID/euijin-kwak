package org.android.go.sopt.presentation.main.search

import org.android.go.sopt.domain.entity.kakao.KakaoSearchWebEntity
import org.android.go.sopt.data.model.BaseResult

sealed class SearchViewState {
    object UnInitialized : SearchViewState()

    object Loading : SearchViewState()

    data class SuccessSearchKeyword(val data: Pair<BaseResult<KakaoSearchWebEntity>, String>) : SearchViewState()

    data class SuccessSearchWeb(val data: BaseResult<KakaoSearchWebEntity>) : SearchViewState()

    object ERROR : SearchViewState()
}
