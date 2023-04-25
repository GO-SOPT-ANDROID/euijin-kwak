package org.android.go.sopt.presentation.main.home

import org.android.go.sopt.presentation.model.MusicItem

sealed class HomeState {
    // create ui state
    object UnInitialized : HomeState()

    object Loading : HomeState()

    data class SuccessMusicList(
        val musicList: List<MusicItem>
    ) : HomeState()

    data class SuccessLatestMusic(
        val musicData: MusicItem
    ) : HomeState()

    data class Error(
        val message: String
    ) : HomeState()
}