package com.makeappssimple.abhimanyu.financemanager.android.utils.extensions

import java.util.*

var Calendar.dayOfMonth: Int
    get() = this[Calendar.DAY_OF_MONTH]
    set(value) {
        this[Calendar.DAY_OF_MONTH] = value
    }

var Calendar.month: Int
    get() = this[Calendar.MONTH]
    set(value) {
        this[Calendar.MONTH] = value
    }

var Calendar.year: Int
    get() = this[Calendar.YEAR]
    set(value) {
        this[Calendar.YEAR] = value
    }

fun Calendar.setDate(
    dayOfMonth: Int,
    month: Int,
    year: Int,
): Calendar {
    this.dayOfMonth = dayOfMonth
    this.month = month
    this.year = year
    return this
}

fun Calendar.formattedDate(): String {
    return this.dayOfMonth.padStartWithZero(length = 2) +
            "/" +
            (this.month + 1).padStartWithZero(length = 2) +
            "/" +
            "${this.year}"
}
