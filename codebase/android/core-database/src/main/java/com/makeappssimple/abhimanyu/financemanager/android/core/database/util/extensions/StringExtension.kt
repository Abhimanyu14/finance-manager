package com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions

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

fun String.capitalizeWords(): String {
    return this
        .split(' ')
        .joinToString(" ") { word ->
            word.lowercase().replaceFirstChar {
                it.uppercaseChar()
            }
        }
}
