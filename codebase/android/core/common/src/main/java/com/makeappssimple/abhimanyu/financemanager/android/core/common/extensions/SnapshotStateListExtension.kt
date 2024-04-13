package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import androidx.compose.runtime.snapshots.SnapshotStateList

public fun <T> SnapshotStateList<T>.addIfDoesNotContainItemElseRemove(
    item: T,
) {
    if (this.contains(item)) {
        this.remove(item)
    } else {
        this.add(item)
    }
}
