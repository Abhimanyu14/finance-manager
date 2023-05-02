package com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model

import androidx.annotation.VisibleForTesting
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.currency.formattedCurrencyValue
import com.squareup.moshi.JsonClass
import kotlin.math.abs
import java.util.Currency

@VisibleForTesting
internal const val CURRENCY_CODE_INR = "INR"

@JsonClass(generateAdapter = false)
data class Amount(
    val currency: Currency = Currency.getInstance(CURRENCY_CODE_INR),
    val value: Long = 0,
) {
    fun toNonSignedString(): String {
        return toSignedString(
            isPositive = false,
            isNegative = false
        )
    }

    fun toSignedString(
        isPositive: Boolean = false,
        isNegative: Boolean = false,
    ): String {
        val formattedValue = formattedCurrencyValue(
            value = abs(value),
        )
        if (isPositive) {
            return "+ ${currency.symbol}$formattedValue"
        }
        if (isNegative) {
            return "- ${currency.symbol}$formattedValue"
        }
        return "${currency.symbol}$formattedValue"
    }

    override fun toString(): String {
        val formattedValue = formattedCurrencyValue(
            value = abs(value),
        )
        return if (value >= 0) {
            "${currency.symbol}$formattedValue"
        } else {
            "- ${currency.symbol}$formattedValue"
        }
    }

    operator fun plus(
        amount: Amount,
    ): Amount {
        return Amount(
            currency = currency,
            value = value + amount.value,
        )
    }

    operator fun minus(
        amount: Amount,
    ): Amount {
        return Amount(
            currency = currency,
            value = value - amount.value,
        )
    }
}
