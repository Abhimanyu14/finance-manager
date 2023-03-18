package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

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
    get() = this[Calendar.HOUR_OF_DAY]
    set(value) {
        this[Calendar.HOUR_OF_DAY] = value
    }

var Calendar.minute: Int
    get() = this[Calendar.MINUTE]
    set(value) {
        this[Calendar.MINUTE] = value
    }

var Calendar.second: Int
    get() = this[Calendar.SECOND]
    set(value) {
        this[Calendar.SECOND] = value
    }

var Calendar.milliSecond: Int
    get() = this[Calendar.MILLISECOND]
    set(value) {
        this[Calendar.MILLISECOND] = value
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
    second: Int = 0,
    milliSecond: Int = 0,
): Calendar {
    this.hour = hour
    this.minute = minute
    this.second = second
    this.milliSecond = milliSecond
    return this
}

fun Calendar.setStartOfDayTime(): Calendar {
    this.hour = 0
    this.minute = 0
    this.second = 0
    this.milliSecond = 0
    return this
}

fun Calendar.setEndOfDayTime(): Calendar {
    this.hour = 23
    this.minute = 59
    this.second = 59
    this.milliSecond = 999
    return this
}

fun Calendar.formattedDate(): String {
    return DateFormat.format("dd MMM, yyyy", this).toString()
}

fun Calendar.formattedTime(): String {
    return DateFormat.format("hh:mm a", this).toString()
        .replace("am", "AM")
        .replace("pm", "PM")
}

fun Calendar.formattedDateAndTime(): String {
    return DateFormat.format("yyyy-MMM-dd, hh-mm a", this).toString()
        .replace("am", "AM")
        .replace("pm", "PM")
}

fun Calendar.formattedReadableDateAndTime(): String {
    return "${formattedDate()} at ${formattedTime()}"
}
