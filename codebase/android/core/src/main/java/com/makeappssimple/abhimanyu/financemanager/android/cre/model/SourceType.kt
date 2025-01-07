package com.makeappssimple.abhimanyu.financemanager.android.cre.model

// TODO(Abhi): Clean up deprecated class
@Deprecated(
    message = "Maintained only for migration",
    replaceWith = ReplaceWith(
        expression = "AccountType",
    ),
)
public enum class SourceType(
    public val title: String,
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
