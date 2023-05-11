package org.android.go.sopt.presentation.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.android.go.sopt.domain.entity.sopt.SoptSignUpRequestEntity
import org.android.go.sopt.domain.repository.SoptRepository
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val soptRepository: SoptRepository) : ViewModel() {
    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.UnInitialized)
    val signUpState: StateFlow<SignUpState> get() = _signUpState

    fun checkDuplicatedId(id:String) {
        viewModelScope.launch {
            soptRepository.getUserInfo(id)?.data?.Id?.let {
                _signUpState.value = SignUpState.Loading
                _signUpState.value = SignUpState.DuplicateId(it)
            } ?: kotlin.run {
                _signUpState.value = SignUpState.NonDuplicateId
            }
        }
    }

    fun signUp(id: String, password: String, name: String, skill: String) {
        viewModelScope.launch {
            soptRepository.postSignUp(SoptSignUpRequestEntity(id, password, name, skill))?.let {
                _signUpState.value = SignUpState.Loading
                _signUpState.value = SignUpState.SuccessSignUp(it)
            } ?: kotlin.run {
                _signUpState.value = SignUpState.Error
            }
        }
    }
}