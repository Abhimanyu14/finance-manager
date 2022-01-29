package com.makeappssimple.abhimanyu.financemanager.android.utils

import java.text.NumberFormat
import java.util.*

fun formattedCurrencyValue(
    value: Long,
): String {
    return NumberFormat.getNumberInstance(Locale.getDefault()).format(value)
}