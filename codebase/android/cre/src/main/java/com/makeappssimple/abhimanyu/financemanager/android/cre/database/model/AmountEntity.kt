package com.makeappssimple.abhimanyu.financemanager.android.cre.database.model

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.constants.CurrencyCodeConstants
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.util.currency.formattedCurrencyValue
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.serializer.CurrencySerializer
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.Serializable
import java.util.Currency
import kotlin.math.abs

@Serializable
public data class AmountEntity(
    @EncodeDefault
    @Serializable(CurrencySerializer::class)
    public val currency: Currency = Currency.getInstance(CurrencyCodeConstants.INR),

    @EncodeDefault
    public val value: Long = 0,
) {
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
}

public fun AmountEntity.toNonSignedString(): String {
    return toSignedString(
        isPositive = false,
        isNegative = false
    )
}

public fun AmountEntity.toSignedString(
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

public operator fun AmountEntity.plus(
    amount: AmountEntity,
): AmountEntity {
    return AmountEntity(
        currency = currency,
        value = value + amount.value,
    )
}

public operator fun AmountEntity.minus(
    amount: AmountEntity,
): AmountEntity {
    return AmountEntity(
        currency = currency,
        value = value - amount.value,
    )
}

public fun AmountEntity.asExternalModel(): Amount {
    return Amount(
        currency = currency,
        value = value,
    )
}
