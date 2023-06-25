package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

fun Int.isNotZero(): Boolean {
    return this != 0
}

fun Int?.orZero(): Int {
    return if (this.isNull()) {
        0
    } else {
        this
    }
}
