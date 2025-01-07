package com.makeappssimple.abhimanyu.financemanager.android.core.common.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

public sealed interface MyResult<out T> {
    public data object Loading : MyResult<Nothing>

    public data class Error(
        val exception: Throwable? = null,
    ) : MyResult<Nothing>

    public data class Success<T>(
        val data: T,
    ) : MyResult<T>
}

public fun <T> Flow<T>.asResult(): Flow<MyResult<T>> {
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
