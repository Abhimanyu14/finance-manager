package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState

@Stable
internal class TransactionForValuesScreenUIState(
    val isBottomSheetVisible: Boolean,
    val transactionForIdToDelete: Int?,
    val transactionForValuesIsUsedInTransactions: List<Boolean>,
    val transactionForValues: List<TransactionFor>,
    val screenBottomSheetType: TransactionForValuesScreenBottomSheetType,
) : ScreenUIState
