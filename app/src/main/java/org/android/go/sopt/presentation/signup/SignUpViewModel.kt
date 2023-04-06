package org.android.go.sopt.presentation.signup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.android.go.sopt.domain.repository.UserEntity
import org.android.go.sopt.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    fun updateUser(user:UserEntity) {

    }

}