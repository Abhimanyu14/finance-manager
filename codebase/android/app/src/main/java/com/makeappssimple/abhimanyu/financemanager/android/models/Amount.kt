package com.makeappssimple.abhimanyu.financemanager.android.models

enum class Currency {
    INR,
    USD,
}

data class Amount(
    val currency: Currency = Currency.INR,
    val value: Float = 0F,
) {
    override fun toString(): String {
        return "$value $currency"
    }
}
