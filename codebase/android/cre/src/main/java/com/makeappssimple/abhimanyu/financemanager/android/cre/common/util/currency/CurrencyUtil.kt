package com.makeappssimple.abhimanyu.financemanager.android.cre.common.util.currency

import java.text.DecimalFormat

public fun formattedCurrencyValue(
    value: Long,
): String {
    // return NumberFormat.getCurrencyInstance(Locale("en", "in")).format(value)

    // Source - https://stackoverflow.com/a/18544311/9636037
    return DecimalFormat("##,##,##0").format(value)
}
