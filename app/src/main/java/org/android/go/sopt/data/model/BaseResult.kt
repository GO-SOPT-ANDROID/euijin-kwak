package org.android.go.sopt.data.model

sealed class BaseResult<out T> {
    data class Success<out T>(val data: T) : BaseResult<T>()
    data class Error(val errorMessage: String) : BaseResult<Nothing>()
}