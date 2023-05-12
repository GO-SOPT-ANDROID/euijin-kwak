package org.android.go.sopt.extension

import org.android.go.sopt.data.model.BaseResult

inline fun <T, R> BaseResult<T>.transform(transformBlock: (T) -> R): BaseResult<R> {
    return when (this) {
        is BaseResult.Success -> BaseResult.Success(transformBlock(data))
        is BaseResult.Error -> this
    }
}