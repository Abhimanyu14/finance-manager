package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import kotlin.contracts.contract

fun Boolean?.isTrue(): Boolean {
    contract {
        returns(true) implies (this@isTrue != null)
    }
    return this == true
}

fun Boolean?.isFalse(): Boolean {
    contract {
        returns(true) implies (this@isFalse != null)
    }
    return this == false
}

fun Boolean?.orFalse(): Boolean {
    return if (this.isNull()) {
        false
    } else {
        this
    }
}
