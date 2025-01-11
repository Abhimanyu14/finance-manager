package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.bottomsheet.ViewTransactionScreenBottomSheetType

@Stable
internal class ViewTransactionScreenUIStateEvents(
    val deleteTransaction: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val navigateToEditTransactionScreen: (transactionId: Int) -> Unit = {},
    val navigateToViewTransactionScreen: (transactionId: Int) -> Unit = {},
    val onRefundButtonClick: (transactionId: Int) -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val setScreenBottomSheetType: (ViewTransactionScreenBottomSheetType) -> Unit = {},
    val setTransactionIdToDelete: (Int?) -> Unit = {},
) : ScreenUIStateEvents
