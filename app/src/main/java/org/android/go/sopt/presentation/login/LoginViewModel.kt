package org.android.go.sopt.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.android.go.sopt.data.model.sopt.SoptLoginRequest
import org.android.go.sopt.domain.entity.sopt.SoptLoginResponseEntity
import org.android.go.sopt.domain.repository.SoptRepository
import org.android.go.sopt.presentation.UIState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val soptRepository: SoptRepository) : ViewModel() {

    private val _loginStateFlow = MutableStateFlow<UIState<SoptLoginResponseEntity>>(UIState.UnInitialized)
    val loginStateFlow: StateFlow<UIState<SoptLoginResponseEntity>> get() = _loginStateFlow

    fun login(id: String, password: String) {
        viewModelScope.launch {
            _loginStateFlow.value = UIState.Loading
            soptRepository.postLogin(SoptLoginRequest(id, password))
            _loginStateFlow.value = soptRepository.postLogin(SoptLoginRequest(id, password))?.let {
                UIState.Success(it)
            } ?: kotlin.run {
                UIState.Error
            }
        }
    }
}