package org.android.go.sopt.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import org.android.go.sopt.domain.repository.UserEntity
import org.android.go.sopt.domain.repository.UserRepository
import org.android.go.sopt.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: UserRepository): BaseViewModel() {

    private val _signUpState = MutableLiveData<SignUpState>(SignUpState.UnInitialized)
    val signUpState:LiveData<SignUpState> get() = _signUpState

    fun updateUser(user:UserEntity) {
        launch {
            withContext(Dispatchers.IO) { repository.updateUser(user) }
            _signUpState.postValue(SignUpState.SuccessSaveUser)
        }
    }
}