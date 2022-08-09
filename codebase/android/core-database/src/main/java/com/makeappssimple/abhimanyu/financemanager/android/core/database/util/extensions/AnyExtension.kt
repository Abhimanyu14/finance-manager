package com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions

fun Any?.isNull(): Boolean {
    return this == null
}

fun Any?.isNotNull(): Boolean {
    return this != null
}
