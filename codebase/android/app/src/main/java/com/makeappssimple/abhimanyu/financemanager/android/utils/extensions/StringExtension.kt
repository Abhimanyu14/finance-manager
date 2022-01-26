package com.makeappssimple.abhimanyu.financemanager.android.utils.extensions

fun String?.isNotNullOrBlank(): Boolean {
    return this != null && this.isNotBlank()
}

fun Any.padStartWithZero(
    length: Int,
): String {
    return this.toString().padStart(
        length = length,
        padChar = '0',
    )
}
