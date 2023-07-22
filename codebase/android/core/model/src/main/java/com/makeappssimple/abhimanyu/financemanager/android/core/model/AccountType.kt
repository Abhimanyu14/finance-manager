package com.makeappssimple.abhimanyu.financemanager.android.core.model

enum class AccountType(
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

val AccountType.sortOrder: Int
    get() = when (this) {
        AccountType.CASH -> {
            1
        }

        AccountType.BANK -> {
            2
        }

        AccountType.E_WALLET -> {
            3
        }
    }
