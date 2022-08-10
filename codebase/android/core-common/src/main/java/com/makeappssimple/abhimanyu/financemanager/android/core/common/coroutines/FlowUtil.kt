package com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

fun Flow<Boolean>.defaultBooleanStateIn(
    scope: CoroutineScope,
): StateFlow<Boolean> {
    return this.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 5000,
        ),
        initialValue = false,
    )
}

fun <T> Flow<List<T>>.defaultListStateIn(
    scope: CoroutineScope,
): StateFlow<List<T>> {
    return this.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 5000,
        ),
        initialValue = emptyList(),
    )
}

fun <T> Flow<T>.defaultObjectStateIn(
    scope: CoroutineScope,
): StateFlow<T?> {
    return this.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 5000,
        ),
        initialValue = null,
    )
}

