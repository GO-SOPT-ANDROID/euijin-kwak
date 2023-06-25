package org.android.go.sopt.presentation.main.home

import org.android.go.sopt.domain.entity.music.SoptGetMusicData

sealed class HomeState {
    object UnInitialized: HomeState()

    object Loading: HomeState()

    data class SuccessGetMusicList(
        val data: SoptGetMusicData
    ): HomeState()

    object SuccessPostMusic: HomeState()

    object Error: HomeState()
}