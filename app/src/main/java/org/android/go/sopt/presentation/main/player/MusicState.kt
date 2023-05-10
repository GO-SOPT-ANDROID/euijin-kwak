package org.android.go.sopt.presentation.main.player

import org.android.go.sopt.presentation.model.MusicItem

sealed class MusicState {
    // create ui state
    object UnInitialized : MusicState()

    object Loading : MusicState()

    data class SuccessMusicList(
        val musicList: List<MusicItem>
    ) : MusicState()

    data class SuccessLatestMusic(
        val musicData: MusicItem
    ) : MusicState()

    data class Error(
        val message: String
    ) : MusicState()
}