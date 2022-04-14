package com.makeappssimple.abhimanyu.financemanager.android.entities.amount

import androidx.annotation.VisibleForTesting
import com.makeappssimple.abhimanyu.financemanager.android.utils.formattedCurrencyValue
import com.squareup.moshi.JsonClass
import java.util.Currency
import kotlin.math.abs

@VisibleForTesting
internal const val CURRENCY_CODE_INR = "INR"

@JsonClass(generateAdapter = false)
data class Amount(
    val currency: Currency = Currency.getInstance(CURRENCY_CODE_INR),
    val value: Long = 0,
) {
    fun toSignedString(): String {
        val formattedValue = formattedCurrencyValue(
            value = abs(value),
        )
        return if (value > 0) {
            "+ ${currency.symbol}$formattedValue"
        } else {
            "- ${currency.symbol}$formattedValue"
        }
    }

    override fun toString(): String {
        val formattedValue = formattedCurrencyValue(
            value = abs(value),
        )
        return if (value > 0) {
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
}
