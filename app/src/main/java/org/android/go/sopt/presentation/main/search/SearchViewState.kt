package org.android.go.sopt.presentation.main.search

import org.android.go.sopt.domain.entity.kakao.KakaoSearchWebEntity

sealed class SearchViewState {
    object UnInitialized : SearchViewState()

    object Loading : SearchViewState()

    data class SuccessSearchKeyword(val data: Pair<List<String>, String>) : SearchViewState()

    data class SuccessSearchWeb(val data: KakaoSearchWebEntity) : SearchViewState()

    object ERROR : SearchViewState()
}
