package com.makeappssimple.abhimanyu.financemanager.android.utils.extensions

import android.text.format.DateFormat
import java.util.Calendar

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

var Calendar.hour: Int
    get() = this[Calendar.HOUR]
    set(value) {
        this[Calendar.HOUR] = value
    }

var Calendar.minute: Int
    get() = this[Calendar.MINUTE]
    set(value) {
        this[Calendar.MINUTE] = value
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

fun Calendar.setTime(
    hour: Int,
    minute: Int,
): Calendar {
    this.hour = hour
    this.minute = minute
    return this
}

fun Calendar.formattedDate(): String {
    return DateFormat.format("dd/MM/yyyy", this).toString()
}

fun Calendar.formattedTime(): String {
    return DateFormat.format("hh:mm a", this).toString()
}
