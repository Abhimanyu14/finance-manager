package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class ViewTransactionScreenUIState(
    val isBottomSheetVisible: Boolean = false,
    val isLoading: Boolean = true,
    val transactionIdToDelete: Int? = null,
    val refundTransactionListItemData: ImmutableList<TransactionListItemData>? = persistentListOf(),
    val originalTransactionListItemData: TransactionListItemData? = null,
    val transactionListItemData: TransactionListItemData? = null,
    val screenBottomSheetType: ViewTransactionScreenBottomSheetType = ViewTransactionScreenBottomSheetType.None,
) : ScreenUIState
