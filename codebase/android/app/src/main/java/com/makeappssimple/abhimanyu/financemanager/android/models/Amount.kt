package com.makeappssimple.abhimanyu.financemanager.android.models

import com.makeappssimple.abhimanyu.financemanager.android.utils.formattedCurrencyValue
import java.util.*

data class Amount(
    val currency: Currency = Currency.getInstance("INR"),
    val value: Long = 0,
) {
    override fun toString(): String {
        val formattedValue = formattedCurrencyValue(
            value = value,
        )

        return "${currency.symbol} $formattedValue"
    }
}
