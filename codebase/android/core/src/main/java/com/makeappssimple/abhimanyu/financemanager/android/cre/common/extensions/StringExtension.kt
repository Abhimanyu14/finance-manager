package com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions

import kotlin.contracts.contract

public fun String?.isNotNullOrBlank(): Boolean {
    contract {
        returns(true) implies (this@isNotNullOrBlank != null)
    }
    return !this.isNullOrBlank()
}

public fun Any.padStartWithZero(
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

public fun String.capitalizeWords(): String {
    return this
        .split(' ')
        .joinToString(" ") { word ->
            word.lowercase().replaceFirstChar {
                it.uppercaseChar()
            }
        }
}

public fun String.equalsIgnoringCase(
    other: String,
): Boolean {
    return this.equals(
        other = other,
        ignoreCase = true,
    )
}

public fun String.filterDigits(): String {
    return this.filter {
        it.isDigit()
    }
}

public fun String.toIntOrZero(): Int {
    return try {
        this.toInt()
    } catch (
        numberFormatException: NumberFormatException,
    ) {
        0
    }
}

public fun String.toLongOrZero(): Long {
    return try {
        this.toLong()
    } catch (
        numberFormatException: NumberFormatException,
    ) {
        0L
    }
}
