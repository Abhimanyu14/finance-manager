package com.makeappssimple.abhimanyu.financemanager.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class SourceType(
    val title: String,
) {
    BANK(
        title = "Bank",
    ),
    CASH(
        title = "Cash",
    ),
    E_WALLET(
        title = "E-Wallet",
    ),
}

@JsonClass(generateAdapter = true)
data class Source(
    @Json(name = "balance_amount")
    val balanceAmount: Amount = Amount(
        value = 0,
    ),
    val id: Int = 0,
    val type: SourceType = SourceType.CASH,
    val name: String,
)
