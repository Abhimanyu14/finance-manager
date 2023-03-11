package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

fun String?.isNotNullOrBlank(): Boolean {
    return !this.isNullOrBlank()
}

fun Any.padStartWithZero(
    length: Int,
): String {
    if (length > 0) {
        return this.toString().padStart(
            length = length,
            padChar = '0',
        )
    }
    return this.toString()
}

fun String.capitalizeWords(): String {
    return this
        .split(' ')
        .joinToString(" ") { word ->
            word.lowercase().replaceFirstChar {
                it.uppercaseChar()
            }
        }
}

fun String.equalsIgnoringCase(
    other: String,
): Boolean {
    return this.equals(
        other = other,
        ignoreCase = true,
    )
}
