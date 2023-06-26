package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

fun Float.isNotZero(): Boolean {
    return this.toInt() != 0
}

fun Float?.orZero(): Float {
    return this ?: 0F
}
