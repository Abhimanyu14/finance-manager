package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Immutable
internal class TransactionForValuesScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setScreenBottomSheetType: (TransactionForValuesScreenBottomSheetType) -> Unit,
    val setTransactionForIdToDelete: (Int?) -> Unit,
) : ScreenUIStateEvents
