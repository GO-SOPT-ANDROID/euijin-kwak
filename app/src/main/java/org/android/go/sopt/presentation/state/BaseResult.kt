package org.android.go.sopt.presentation.state

sealed class BaseResult<out T> {
    data class Success<out T>(val data: T) : BaseResult<T>()
    data class Error(val errorMessage: String) : BaseResult<Nothing>()
}