package com.makeappssimple.abhimanyu.financemanager.android.core.model

@Deprecated(
    message = "Maintained only for migration",
    replaceWith = ReplaceWith(
        expression = "AccountType",
    ),
)
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
