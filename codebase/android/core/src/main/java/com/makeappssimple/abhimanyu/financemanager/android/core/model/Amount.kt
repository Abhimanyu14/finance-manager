package com.makeappssimple.abhimanyu.financemanager.android.core.model

import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.CurrencyCodeConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.currency.formattedCurrencyValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.serializer.CurrencySerializer
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.Serializable
import java.util.Currency
import kotlin.math.abs

@Serializable
public data class Amount(
    @EncodeDefault
    @Serializable(CurrencySerializer::class)
    public val currency: Currency = Currency.getInstance(CurrencyCodeConstants.INR),

    @EncodeDefault
    public val value: Long = 0,
) : Comparable<Amount> {
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

    override fun compareTo(
        other: Amount,
    ): Int {
        return (value - other.value).toInt()
    }
}

public fun Amount.toNonSignedString(): String {
    return toSignedString(
        isPositive = false,
        isNegative = false
    )
}

public fun Amount.toSignedString(
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

public operator fun Amount.plus(
    amount: Amount,
): Amount {
    return Amount(
        currency = currency,
        value = value + amount.value,
    )
}

public operator fun Amount.minus(
    amount: Amount,
): Amount {
    return Amount(
        currency = currency,
        value = value - amount.value,
    )
}

public fun Amount?.orEmpty(): Amount {
    return if (this.isNull()) {
        Amount()
    } else {
        this
    }
}
