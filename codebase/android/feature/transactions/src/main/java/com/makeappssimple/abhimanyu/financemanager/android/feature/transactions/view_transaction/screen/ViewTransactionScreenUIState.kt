package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData

@Stable
internal class ViewTransactionScreenUIState(
    val isBottomSheetVisible: Boolean,
    val transactionIdToDelete: Int?,
    val refundTransactionListItemData: List<TransactionListItemData>?,
    val originalTransactionListItemData: TransactionListItemData?,
    val transactionListItemData: TransactionListItemData?,
    val screenBottomSheetType: ViewTransactionScreenBottomSheetType,
) : ScreenUIState
