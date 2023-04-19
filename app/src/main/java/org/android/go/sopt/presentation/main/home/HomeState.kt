package org.android.go.sopt.presentation.main.home

import org.android.go.sopt.domain.entity.MusicData

sealed class HomeState {
    // create ui state
    object UnInitialized : HomeState()

    object Loading : HomeState()

    data class SuccessMusicList(
        val musicList: List<MusicData>
    ) : HomeState()

    data class SuccessLatestMusic(
        val musicData: MusicData
    ) : HomeState()

    data class Error(
        val message: String
    ) : HomeState()
}