package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

fun (() -> Unit)?.orEmpty(): () -> Unit {
    return this ?: {}
}
