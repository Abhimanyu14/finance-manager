package com.makeappssimple.abhimanyu.financemanager.android.entities.amount

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.Currency

data class AmountJson(
    val currency: String,
    val value: Long = 0,
)

class AmountJsonAdapter {
    @ToJson
    fun toJson(amount: Amount): AmountJson {
        return AmountJson(
            currency = amount.currency.currencyCode,
            value = amount.value,
        )
    }

    @FromJson
    fun fromJson(
        amountJson: AmountJson,
    ): Amount {
        return Amount(
            currency = Currency.getInstance(amountJson.currency),
            value = amountJson.value,
        )
    }
}
