package org.android.go.sopt.presentation.signup

import org.android.go.sopt.domain.repository.UserEntity

sealed class SignUpState {
    object UnInitialized : SignUpState()

    object SuccessSaveUser : SignUpState()

    object Error : SignUpState()
}



