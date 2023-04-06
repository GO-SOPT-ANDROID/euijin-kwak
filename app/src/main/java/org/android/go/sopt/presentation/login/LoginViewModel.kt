package org.android.go.sopt.presentation.login

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import org.android.go.sopt.domain.repository.UserRepository
import org.android.go.sopt.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository) : BaseViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.UnInitialized)
    val loginState: StateFlow<LoginState> get() = _loginState

    fun readUser() {
        launch {
            withContext(Dispatchers.IO) {
                repository.readUser().collectLatest {
                    _loginState.value = if (it.id.isNotEmpty() && it.password.isNotEmpty()) {
                        LoginState.SuccessGetUserData(it)
                    } else {
                        LoginState.LoginFail
                    }
                }
            }
        }
    }
}