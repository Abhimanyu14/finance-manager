package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

public fun <T> ImmutableList<T>?.orEmpty(): ImmutableList<T> = this ?: persistentListOf()
