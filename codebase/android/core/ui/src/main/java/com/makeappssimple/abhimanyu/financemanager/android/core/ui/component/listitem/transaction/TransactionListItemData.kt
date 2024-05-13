package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor

@Immutable
public data class TransactionListItemData(
    val isDeleteButtonEnabled: Boolean = false,
    val isDeleteButtonVisible: Boolean = false,
    val isEditButtonVisible: Boolean = false,
    val isExpanded: Boolean = false,
    val isInSelectionMode: Boolean = false,
    val isRefundButtonVisible: Boolean = false,
    val isSelected: Boolean = false,
    val transactionId: Int,
    val amountColor: MyColor,
    val amountText: String,
    val dateAndTimeText: String,
    val emoji: String,
    val accountFromName: String?,
    val accountToName: String?,
    val title: String,
    val transactionForText: String,
)
