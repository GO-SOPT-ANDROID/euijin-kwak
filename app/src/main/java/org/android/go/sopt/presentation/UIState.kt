package org.android.go.sopt.presentation

sealed class UIState<out T>(val _data: T?) {
    object UnInitialized : UIState<Nothing>(_data = null)
    object Loading : UIState<Nothing>(_data = null)
    object Error : UIState<Nothing>(_data = null)
    data class Success<out R>(val data: R) : UIState<R>(_data = data)
}