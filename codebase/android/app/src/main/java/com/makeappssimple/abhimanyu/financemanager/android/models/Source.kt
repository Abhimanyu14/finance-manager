package com.makeappssimple.abhimanyu.financemanager.android.models

enum class SourceType {
    BANK,
    CASH,
    E_WALLET,
}

data class Source(
    val balanceAmount: Amount = Amount(
        value = 0F,
    ),
    val id: Int,
    val type: SourceType = SourceType.CASH,
    val name: String,
)
