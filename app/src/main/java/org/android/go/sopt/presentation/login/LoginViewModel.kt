package org.android.go.sopt.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _loginState = MutableLiveData<LoginState>(LoginState.UnInitialized)
    val loginState: LiveData<LoginState> get() = _loginState

    fun readUser() {
        launch {
            withContext(Dispatchers.IO) {
                repository.readUser().collectLatest {
                    _loginState.postValue(if (it.id.isNotEmpty() && it.password.isNotEmpty()) {
                        LoginState.SuccessGetUserData(it)
                    } else {
                        LoginState.LoginFail
                    })
                }
            }
        }
    }

    fun setAutoLogin(isAutoLogin: Boolean) {
        launch {
            withContext(Dispatchers.IO) {
                repository.setAutoLogin(isAutoLogin)
            }
        }
    }

    fun getAutoLogin() {
        launch {
            withContext(Dispatchers.IO) {
                repository.getAutoLogin().collectLatest {
                    _loginState.postValue(LoginState.SuccessGetAutoLogin(it))
                }
            }
        }
    }
}