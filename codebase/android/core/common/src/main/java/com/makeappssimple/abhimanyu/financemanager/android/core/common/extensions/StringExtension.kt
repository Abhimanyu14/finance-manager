package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import kotlin.contracts.contract

fun String?.isNotNullOrBlank(): Boolean {
    contract {
        returns(true) implies (this@isNotNullOrBlank != null)
    }
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

fun String.filterDigits(): String {
    return this.filter {
        it.isDigit()
    }
}
