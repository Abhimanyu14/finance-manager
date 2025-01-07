package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

public fun Float.isNotZero(): Boolean {
    return this.toInt() != 0
}

public fun Float?.orZero(): Float {
    return this ?: 0F
}
