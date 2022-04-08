package com.makeappssimple.abhimanyu.financemanager.android.utils.extensions

fun String?.isNotNullOrBlank(): Boolean {
    return this != null && this.isNotBlank()
}

fun Any.padStartWithZero(
    length: Int,
): String {
    if (length < 0) {
        return this.toString().padStart(
            length = 0,
            padChar = '0',
        )
    }
    return this.toString().padStart(
        length = length,
        padChar = '0',
    )
}
