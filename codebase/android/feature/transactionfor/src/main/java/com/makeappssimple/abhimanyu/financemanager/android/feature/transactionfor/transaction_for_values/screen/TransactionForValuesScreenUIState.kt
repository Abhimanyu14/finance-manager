package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import kotlinx.collections.immutable.ImmutableList

@Stable
internal data class TransactionForValuesScreenUIState(
    val isBottomSheetVisible: Boolean,
    val transactionForIdToDelete: Int?,
    val transactionForValuesIsUsedInTransactions: ImmutableList<Boolean>,
    val transactionForValues: ImmutableList<TransactionFor>,
    val screenBottomSheetType: TransactionForValuesScreenBottomSheetType,
) : ScreenUIState
