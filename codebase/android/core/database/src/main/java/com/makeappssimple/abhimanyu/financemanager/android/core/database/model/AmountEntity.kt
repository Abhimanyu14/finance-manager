package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.CurrencyCodeConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.currency.formattedCurrencyValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.serializer.CurrencySerializer
import kotlin.math.abs
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import java.util.Currency

@Serializable
data class AmountEntity @OptIn(ExperimentalSerializationApi::class) constructor(
    @EncodeDefault
    @Serializable(CurrencySerializer::class)
    val currency: Currency = Currency.getInstance(CurrencyCodeConstants.INR),

    @EncodeDefault
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
        amount: AmountEntity,
    ): AmountEntity {
        return AmountEntity(
            currency = currency,
            value = value + amount.value,
        )
    }

    operator fun minus(
        amount: AmountEntity,
    ): AmountEntity {
        return AmountEntity(
            currency = currency,
            value = value - amount.value,
        )
    }
}

fun AmountEntity.asExternalModel(): Amount {
    return Amount(
        currency = currency,
        value = value,
    )
}