package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.state

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.bottomsheet.TransactionForValuesScreenBottomSheetType

@Immutable
internal class TransactionForValuesScreenUIStateEvents(
    val deleteTransactionFor: () -> Unit = {},
    val navigateToAddTransactionForScreen: () -> Unit = {},
    val navigateToEditTransactionForScreen: (transactionForId: Int) -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val updateScreenBottomSheetType: (TransactionForValuesScreenBottomSheetType) -> Unit = {},
    val updateTransactionForIdToDelete: (Int?) -> Unit = {},
) : ScreenUIStateEvents
