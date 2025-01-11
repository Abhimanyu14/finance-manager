package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

public fun <T> List<T>?.orEmpty(): ImmutableList<T> {
    return this?.toImmutableList() ?: persistentListOf()
}

public inline fun <T, R> List<T>?.map(transform: (T) -> R): ImmutableList<R> {
    return (this as? Iterable<T>)?.map(transform)?.toImmutableList().orEmpty()
}

public inline fun <T, R> List<T>.mapIndexed(transform: (index: Int, T) -> R): ImmutableList<R> {
    return (this as? Iterable<T>)?.mapIndexed(transform)?.toImmutableList().orEmpty()
}

public inline fun <reified R> List<*>.filterIsInstance(): ImmutableList<R> {
    return filterIsInstanceTo(ArrayList<R>()).toImmutableList()
}

public inline fun <T> List<T>?.filter(predicate: (T) -> Boolean): ImmutableList<T> {
    return (this as? Iterable<T>)?.filter(predicate)?.toImmutableList().orEmpty()
}

public fun <K, V> Map<out K, V>.toImmutableList(): ImmutableList<Pair<K, V>> {
    return this.toList().toImmutableList()
}

public fun <T> List<T>.sortedWith(comparator: Comparator<in T>): ImmutableList<T> {
    return (this as? Iterable<T>)?.sortedWith(comparator)?.toImmutableList().orEmpty()
}

public fun <T> Iterable<T>.distinct(): ImmutableList<T> {
    return this.toMutableSet().toList().toImmutableList()
}

public inline fun <T, K> List<T>.groupBy(keySelector: (T) -> K): Map<K, ImmutableList<T>> {
    return (this as? Iterable<T>)?.groupBy(keySelector)?.mapValues { (_, value) ->
        value.toImmutableList()
    }.orEmpty()
}
