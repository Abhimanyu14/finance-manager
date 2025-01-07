package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
public data class AccountsListItemHeaderData(
    override val type: AccountsListItemType = AccountsListItemType.HEADER,
    val isDefault: Boolean = false,
    val isDeleteEnabled: Boolean = false,
    val isExpanded: Boolean = false,
    val isHeading: Boolean = false,
    val isLowBalance: Boolean = false,
    val isSelected: Boolean = false,
    val icon: ImageVector? = null,
    val accountId: Int? = null,
    val balance: String? = null,
    val name: String,
) : AccountsListItemData
