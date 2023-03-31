package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

fun Long?.orZero(): Long {
    return this ?: 0L
}
