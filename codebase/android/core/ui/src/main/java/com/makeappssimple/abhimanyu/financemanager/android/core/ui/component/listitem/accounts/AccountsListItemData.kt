package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts

sealed interface AccountsListItemData {
    val type: AccountsListItemType
}

enum class AccountsListItemType(
    val title: String,
) {
    CONTENT(
        title = "Content"
    ),
    HEADER(
        title = "Header"
    ),
}
