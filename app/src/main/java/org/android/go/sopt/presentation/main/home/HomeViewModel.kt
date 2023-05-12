package org.android.go.sopt.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.android.go.sopt.domain.entity.reqres.ReqresEntity
import org.android.go.sopt.domain.repository.ReqresRepository
import org.android.go.sopt.presentation.state.UIState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val reqresRepository: ReqresRepository) : ViewModel() {

    private val _userListStateFlow = MutableStateFlow<UIState<ReqresEntity>>(UIState.UnInitialized)
    val userListStateFlow: StateFlow<UIState<ReqresEntity>> get() = _userListStateFlow

    fun getUsers(page: Int) {
        viewModelScope.launch {
            _userListStateFlow.value = UIState.Loading
            reqresRepository.getUsers(page)?.let {
                _userListStateFlow.value = UIState.Success(it)
            } ?: kotlin.run {
                _userListStateFlow.value = UIState.Error
            }
        }
    }
}