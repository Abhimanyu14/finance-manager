package com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions

import kotlin.contracts.contract

public fun Boolean?.isTrue(): Boolean {
    contract {
        returns(true) implies (this@isTrue != null)
    }
    return this == true
}

public fun Boolean?.isFalse(): Boolean {
    contract {
        returns(true) implies (this@isFalse != null)
    }
    return this == false
}

public fun Boolean?.orFalse(): Boolean {
    return if (this.isNull()) {
        false
    } else {
        this
    }
}
