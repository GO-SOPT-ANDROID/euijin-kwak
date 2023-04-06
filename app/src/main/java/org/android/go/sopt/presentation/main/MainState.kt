package org.android.go.sopt.presentation.main

import org.android.go.sopt.domain.repository.UserEntity

sealed class MainState {
    object UnInitialized : MainState()

    data class SuccessUserData(
        val user: UserEntity
    ) : MainState()

    object Error : MainState()
}



