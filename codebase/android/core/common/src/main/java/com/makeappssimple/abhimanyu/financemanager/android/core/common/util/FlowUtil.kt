package com.makeappssimple.abhimanyu.financemanager.android.core.common.util

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

public fun Flow<Boolean>.defaultBooleanStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(
        stopTimeoutMillis = 5000,
    ),
): StateFlow<Boolean> {
    return this.stateIn(
        scope = scope,
        started = started,
        initialValue = false,
    )
}

public fun Flow<Long>.defaultLongStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(
        stopTimeoutMillis = 5000,
    ),
): StateFlow<Long> {
    return this.stateIn(
        scope = scope,
        started = started,
        initialValue = 0L,
    )
}

public fun <T> Flow<ImmutableList<T>>.defaultListStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(
        stopTimeoutMillis = 5000,
    ),
): StateFlow<ImmutableList<T>> {
    return this.stateIn(
        scope = scope,
        started = started,
        initialValue = persistentListOf(),
    )
}

public fun <K, V> Flow<ImmutableMap<K, V>>.defaultMapStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(
        stopTimeoutMillis = 5000,
    ),
): StateFlow<ImmutableMap<K, V>> {
    return this.stateIn(
        scope = scope,
        started = started,
        initialValue = persistentMapOf(),
    )
}

public fun <T> Flow<T>.defaultObjectStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(
        stopTimeoutMillis = 5000,
    ),
    initialValue: T? = null,
): StateFlow<T?> {
    return this.stateIn(
        scope = scope,
        started = started,
        initialValue = initialValue,
    )
}
