package com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions

public fun Int.isNotZero(): Boolean {
    return this != 0
}

public fun Int?.orZero(): Int {
    return if (this.isNull()) {
        0
    } else {
        this
    }
}
