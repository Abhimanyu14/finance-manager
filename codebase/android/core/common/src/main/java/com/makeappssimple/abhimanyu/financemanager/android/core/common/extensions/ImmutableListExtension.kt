package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

public fun <T> ImmutableList<T>?.orEmpty(): ImmutableList<T> {
    return this ?: persistentListOf()
}

public fun <T, R> ImmutableList<T>.map(transform: (T) -> R): ImmutableList<R> {
    return (this as Iterable<T>).map(transform).toImmutableList()
}
