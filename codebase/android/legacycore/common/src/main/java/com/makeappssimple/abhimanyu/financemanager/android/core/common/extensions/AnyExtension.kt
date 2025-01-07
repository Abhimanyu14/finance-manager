package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import kotlin.contracts.contract

public fun Any?.isNull(): Boolean {
    contract {
        returns(true) implies (this@isNull == null)
        returns(false) implies (this@isNull != null)
    }
    return this == null
}

public fun Any?.isNotNull(): Boolean {
    contract {
        returns(false) implies (this@isNotNull == null)
        returns(true) implies (this@isNotNull != null)
    }
    return this != null
}
