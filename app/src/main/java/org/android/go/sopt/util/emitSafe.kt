package org.android.go.sopt.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.android.go.sopt.data.model.BaseResult
import retrofit2.Response

fun <T> emitSafe(block: suspend () -> Response<T>): Flow<BaseResult<T>> = flow {
    try {
        val response = block()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(BaseResult.Success(it))
            }
        } else {
            emit(BaseResult.Error(response.message()))
        }
    } catch (exception: Exception) {
        emit(BaseResult.Error(exception.message ?: "An error occurred"))
    }
}