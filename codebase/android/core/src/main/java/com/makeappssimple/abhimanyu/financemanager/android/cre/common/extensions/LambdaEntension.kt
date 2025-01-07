package com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions

public fun (() -> Unit)?.orEmpty(): () -> Unit {
    return this ?: {}
}
