package org.android.go.sopt.presentation.login

import org.android.go.sopt.domain.repository.UserEntity

sealed class LoginState {
    object UnInitialized : LoginState()

    data class SuccessGetUserData(
        val user: UserEntity
    ) : LoginState()

    data class SuccessGetAutoLogin(
        val isAutoLogin: Boolean
    ) : LoginState()

    object LoginFail : LoginState()

    object Error : LoginState()
}


