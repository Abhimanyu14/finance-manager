package com.makeappssimple.abhimanyu.financemanager.utils

import java.text.DecimalFormat

fun formattedCurrencyValue(
    value: Long,
): String {
    // Source - https://stackoverflow.com/a/18544311/9636037
    return DecimalFormat("##,##,##0").format(value)
}
