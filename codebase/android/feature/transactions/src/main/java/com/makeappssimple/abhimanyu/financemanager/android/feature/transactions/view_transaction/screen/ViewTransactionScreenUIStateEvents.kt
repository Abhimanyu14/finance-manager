package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class ViewTransactionScreenUIStateEvents(
    val deleteTransaction: (transactionId: Int) -> Unit = {},
    val navigateUp: () -> Unit = {},
    val navigateToAddTransactionScreen: (transactionId: Int) -> Unit = {},
    val navigateToEditTransactionScreen: (transactionId: Int) -> Unit = {},
    val navigateToViewTransactionScreen: (transactionId: Int) -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val setScreenBottomSheetType: (ViewTransactionScreenBottomSheetType) -> Unit = {},
    val setTransactionIdToDelete: (Int?) -> Unit = {},
) : ScreenUIStateEvents
