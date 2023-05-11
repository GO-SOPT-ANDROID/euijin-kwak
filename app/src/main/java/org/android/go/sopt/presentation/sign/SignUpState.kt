package org.android.go.sopt.presentation.sign

import org.android.go.sopt.domain.entity.sopt.SoptSignUpResponseEntity

sealed class SignUpState {
    object UnInitialized : SignUpState()
    object Loading : SignUpState()
    object Error : SignUpState()
    data class DuplicateId(val id: String) : SignUpState()
    object NonDuplicateId : SignUpState()
    data class SuccessSignUp(val responseEntity: SoptSignUpResponseEntity) : SignUpState()
}
