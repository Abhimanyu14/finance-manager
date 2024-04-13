package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts

public sealed interface AccountsListItemData {
    public val type: AccountsListItemType
}

public enum class AccountsListItemType(
    public val title: String,
) {
    CONTENT(
        title = "Content"
    ),
    HEADER(
        title = "Header"
    ),
}
