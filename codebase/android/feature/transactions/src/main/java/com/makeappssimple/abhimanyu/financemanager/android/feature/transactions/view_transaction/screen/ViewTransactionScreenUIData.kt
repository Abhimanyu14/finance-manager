package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData

@Immutable
data class ViewTransactionScreenUIData(
    val originalTransactionListItemData: TransactionListItemData? = null,
    val refundTransactionListItemData: List<TransactionListItemData>? = null,
    val transactionListItemData: TransactionListItemData? = null,
) : ScreenUIData