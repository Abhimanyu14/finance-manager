package com.makeappssimple.abhimanyu.financemanager.android.core.common.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface MyResult<out T> {
    data object Loading : MyResult<Nothing>

    data class Error(
        val exception: Throwable? = null,
    ) : MyResult<Nothing>

    data class Success<T>(
        val data: T,
    ) : MyResult<T>
}

fun <T> Flow<T>.asResult(): Flow<MyResult<T>> {
    return this
        .map<T, MyResult<T>> {
            MyResult.Success(it)
        }
        .onStart {
            emit(MyResult.Loading)
        }
        .catch {
            emit(MyResult.Error(it))
        }
}
