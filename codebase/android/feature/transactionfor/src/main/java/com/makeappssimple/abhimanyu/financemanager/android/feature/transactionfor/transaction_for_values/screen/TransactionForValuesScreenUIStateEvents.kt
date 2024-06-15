package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Immutable
internal class TransactionForValuesScreenUIStateEvents(
    val deleteTransactionFor: (id: Int) -> Unit = {},
    val navigateToAddTransactionForScreen: () -> Unit = {},
    val navigateToEditTransactionForScreen: (transactionForId: Int) -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val setScreenBottomSheetType: (TransactionForValuesScreenBottomSheetType) -> Unit = {},
    val setTransactionForIdToDelete: (Int?) -> Unit = {},
) : ScreenUIStateEvents
