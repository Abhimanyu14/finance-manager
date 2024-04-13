package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

public fun (() -> Unit)?.orEmpty(): () -> Unit {
    return this ?: {}
}
